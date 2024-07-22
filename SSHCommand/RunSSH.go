package SSHCommand

import (
	"encoding/json"
	"fmt"
	"github.com/labstack/gommon/log"
	"golang.org/x/crypto/ssh"
	"io"
	"os"
	"strings"
)

type ScutInfo struct {
	Username, Password string
	AcIP, AcName       string
}

type SSHClass struct {
	client   *ssh.Client
	session  *ssh.Session
	err      error
	status   bool      // 记录上一操作是否成功
	out      io.Writer // 用于保存输出SSH运行结果
	ScutInfo           // 保存信息
}

func (s *SSHClass) SetOut(out io.Writer) {
	// 先于 NewSession 被调用
	s.out = out
}

func (s *SSHClass) NewSession() {
	// 每条命令都需要一个新 Session
	if s.status != true {
		log.Errorf("Dial Fails: %s", s.err.Error())
		return
	}
	s.session, s.err = s.client.NewSession()
	s.Assert()

	// 设置输出
	s.session.Stdout = s.out
}

func (s *SSHClass) Close() {
	if s.session != nil {
		s.session.Close()
		s.session = nil
		s.status = true
	}
}

func (s *SSHClass) Assert() {
	if s.err != nil {
		log.Error(s.err.Error())
		s.status = false
		return
	}
	s.status = true
}

func (s *SSHClass) RunCommand(cmd string) {
	s.NewSession()
	defer s.Close()

	if s.status != true {
		log.Errorf("New Session Fails: %s", s.err.Error())
		return
	}
	err := s.session.Run(cmd)
	if err != nil {
		log.Error(err.Error())
	}
}

type accountInfo struct {
	Username, Password string
	AcIP, AcName       string
}

func (s *SSHClass) preLoadInfo() {
	// 预加载信息
	infoFile, err := os.ReadFile("./Account.json")
	if err != nil {
		log.Info(err.Error())
		return
	}
	accInfo := new(accountInfo)
	err = json.Unmarshal(infoFile, accInfo)
	if err != nil {
		return
	}

	s.Username = accInfo.Username
	s.Password = accInfo.Password
	s.AcIP = accInfo.AcIP
	s.AcName = accInfo.AcName
}

func (s *SSHClass) saveInfo() {
	log.Info("saveInfo is called")
	infoFile, err := os.Create("./Account.json")
	if err != nil {
		log.Error(err.Error())
	}

	accInfo := new(accountInfo)
	accInfo.Username = s.Username
	accInfo.Password = s.Password
	accInfo.AcIP = s.AcIP
	accInfo.AcName = s.AcName

	b, err := json.Marshal(accInfo)
	if err != nil {
		log.Error(err.Error())
	}
	infoFile.Write(b)
	infoFile.Close()
}

func (s *SSHClass) RunSetScutInfo(username, password string) {
	log.Info("RunSetScutInfo is called")
	s.preLoadInfo()

	s.Username = username
	s.Password = password
	s.HelpSetInfo()
}

func (s *SSHClass) RunSetAcInfo(acIP, acName string) {
	log.Info("RunSetAcInfo is called")
	s.preLoadInfo()

	s.AcIP = acIP
	s.AcName = acName
	s.HelpSetInfo()
}

func (s *SSHClass) RunWiredLogin() {
	cmd := fmt.Sprintf("chmod +x scutlogin.sh && ./scutlogin.sh")
	log.Info("RunWiredLogin is called")
	s.RunCommand(cmd)
}

func (s *SSHClass) RunWirelessLogin() {
	cmd := fmt.Sprintf("chmod +x wl_login.sh && ./wl_login.sh")
	log.Info("RunWirelessLogin is called")
	s.RunCommand(cmd)
}

func (s *SSHClass) HelpSetInfo() {
	// 辅助函数
	s.saveInfo()

	cmd := fmt.Sprintf("echo %s > SCUT_info.txt", s.Username)
	cmd += fmt.Sprintf(" && echo %s >> SCUT_info.txt", s.Password)
	cmd += fmt.Sprintf(" && echo %s >> SCUT_info.txt", s.AcIP)
	cmd += fmt.Sprintf(" && echo %s >> SCUT_info.txt", s.AcName)
	s.RunCommand(cmd)
}

func (s *SSHClass) RunSyncTime() {
	cmd := fmt.Sprintf("ntpd -n -q -p ntp.aliyun.com")

	log.Info("RunSyncTime is called")
	s.RunCommand(cmd)
}

func (s *SSHClass) RunReboot() {
	cmd := fmt.Sprintf("reboot")

	log.Info("RunReboot is called")
	s.RunCommand(cmd)
}

func (s *SSHClass) RunAutoLogin() {
	log.Info("RunAutoLogin is called")

	cmd := fmt.Sprintf("crontab -l | sed '/.*\\/root\\/scutlogin.sh.*/d' > temp_cron")
	cmd += fmt.Sprintf(" && echo \"5 6 * * 1-5 /root/scutlogin.sh\" >> temp_cron")
	cmd += fmt.Sprintf(" && crontab temp_cron\nrm temp_cron")

	s.RunCommand(cmd)
}

func (s *SSHClass) RunSetNetwork(ip string, dnsArr string, netmask, gateway string) {
	log.Info("RunSetNetwork is called")

	log.Info("Set interface wan")
	if1 := "/etc/config/network.wan." // 接口wan
	cmd := fmt.Sprintf("uci set %sproto=static", if1)
	cmd += fmt.Sprintf(" && uci set %sipaddr=%s", if1, ip)
	cmd += fmt.Sprintf(" && uci set %snetmask=%s", if1, netmask)
	cmd += fmt.Sprintf(" && uci set %sgateway=%s", if1, gateway)

	log.Info(fmt.Sprintf("DNS列表：%s", dnsArr))
	dns := strings.Split(dnsArr, " ")
	dnsConcat := ""
	for _, dnsItem := range dns {
		dnsConcat += dnsItem
		dnsConcat += " "
	}
	dnsConcat = "'" + dnsConcat[:len(dnsConcat)-1] + "'" // 裁剪最后一个空格
	cmd += fmt.Sprintf(" && uci set %sdns=%s", if1, dnsConcat)

	log.Info("Set interface wan6")
	// 增加 interface wan6 选项
	cmd += fmt.Sprintf(" && uci set /etc/config/network.wan6=interface")

	if2 := "/etc/config/network.wan6." // 接口wan6
	cmd += fmt.Sprintf(" && uci set %sproto=dhcpv6", if2)
	cmd += fmt.Sprintf(" && uci set %sreqaddress=try", if2)
	cmd += fmt.Sprintf(" && uci set %sreqprefix=auto", if2)
	cmd += fmt.Sprintf(" && uci set %speerdns=0", if2)
	cmd += fmt.Sprintf(" && uci set %sdns=%s", if2, "'2400:3200::1 2400:3200:baba::1'")

	log.Info("Set dhcp lan")
	if3 := "/etc/config/dhcp.lan." // dhcp lan
	cmd += fmt.Sprintf(" && uci set %sdhcpv4=server", if3)
	cmd += fmt.Sprintf(" && uci set %sra=relay", if3)
	cmd += fmt.Sprintf(" && uci set %sdhcpv6=relay", if3)
	cmd += fmt.Sprintf(" && uci set %sndp=relay", if3)

	log.Info("Set dhcp wan6")
	// 增加 dhcp wan6 选项
	cmd += fmt.Sprintf(" && uci set /etc/config/dhcp.wan6=dhcp")

	if4 := "/etc/config/dhcp.wan6." // dhcp wan6
	cmd += fmt.Sprintf(" && uci set %sdhcpv4=server", if4)
	cmd += fmt.Sprintf(" && uci set %sra=relay", if4)
	cmd += fmt.Sprintf(" && uci set %sdhcpv6=relay", if4)
	cmd += fmt.Sprintf(" && uci set %sndp=relay", if4)
	cmd += fmt.Sprintf(" && uci set %smaster=1", if4)

	cmd += fmt.Sprintf(" && uci add_list %sdns=::1", if4) // 此行必须添加，否则可能因为不存在list而导致错误
	cmd += fmt.Sprintf(" && uci add_list %sdns=::2", if4) // 此行必须添加，否则可能因为不存在list而导致错误
	cmd += fmt.Sprintf(" && uci delete %sdns", if4)       // 使用delete命令删除整个列表
	cmd += fmt.Sprintf(" && uci add_list %sdns=2606:4700:4700::1111", if4)
	cmd += fmt.Sprintf(" && uci add_list %sdns=2001:4860:4860::8888", if4)

	log.Info("Set dhcp wan")
	if5 := "/etc/config/dhcp.wan." // dhcp wan
	cmd += fmt.Sprintf(" && uci set %signore=1", if5)

	log.Info("Delete lan IPV6 Assign")
	if6 := "/etc/config/network.lan."
	cmd += fmt.Sprintf(" && uci set %sip6assign=60", if6) // 用于保证选项存在
	cmd += fmt.Sprintf(" && uci delete %sip6assign", if6)

	cmd += fmt.Sprintf(" && service network restart")
	s.RunCommand(cmd)
	log.Info("SetNetwork finished")
}

func (s *SSHClass) CancelAutoLogin() {
	log.Info("CancelAutoLogin is called")

	cmd := fmt.Sprintf("crontab -l | sed '/.*\\/root\\/scutlogin.sh.*/d' > temp_cron")
	cmd += fmt.Sprintf(" && crontab temp_cron\nrm temp_cron")
	s.RunCommand(cmd)
}

type RouterConfig struct {
	Addr     string `json:"addr"`
	Password string `json:"password"`
	Port     string `json:"port"`
}

func NewRunSSH() *SSHClass {
	configPath := "./config.json"

	buf, err := os.ReadFile(configPath)
	if err != nil {
		log.Error(err.Error())
		f, _ := os.Create(configPath)
		f.Close()
		return nil
	}

	rConfig := new(RouterConfig)
	err = json.Unmarshal(buf, rConfig)
	if err != nil {
		log.Error(err.Error())
		return nil
	}

	config := ssh.ClientConfig{
		Config:          ssh.Config{},
		User:            "root",
		Auth:            []ssh.AuthMethod{ssh.Password(rConfig.Password)},
		HostKeyCallback: ssh.InsecureIgnoreHostKey(),
	}
	SSHObj := new(SSHClass)
	addrPort := fmt.Sprintf("%s:%s", rConfig.Addr, rConfig.Port)
	SSHObj.client, SSHObj.err = ssh.Dial("tcp", addrPort, &config)
	SSHObj.Assert()

	return SSHObj
}
