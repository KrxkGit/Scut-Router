package SSHCommand

import (
	"encoding/json"
	"fmt"
	"github.com/labstack/gommon/log"
	"golang.org/x/crypto/ssh"
	"io"
	"os"
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
	// 遇加载信息
	infoFile, err := os.ReadFile("./Account.json")
	if err != nil {
		log.Info(err.Error())
		return
	}
	accInfo := new(accountInfo)
	json.Unmarshal(infoFile, accInfo)

	s.Username = accInfo.Username
	s.Password = accInfo.Password
	s.AcIP = accInfo.AcIP
	s.AcName = accInfo.AcName
}

func (s *SSHClass) saveInfo() {
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

func (s *SSHClass) RunAutoLogin() {
	log.Info("RunAutoLogin is called")

	cmd := fmt.Sprintf("crontab -l | sed '/.*\\/root\\/scutlogin.sh.*/d' > temp_cron")
	cmd += fmt.Sprintf(" && echo \"5 6 * * 1-5 /root/scutlogin.sh\" >> temp_cron")
	cmd += fmt.Sprintf(" && crontab temp_cron\nrm temp_cron")

	s.RunCommand(cmd)
}

func (s *SSHClass) CancelAutoLogin() {
	log.Info("CancelAutoLogin is called")

	cmd := fmt.Sprintf("crontab -l | sed '/.*\\/root\\/scutlogin.sh.*/d' > temp_cron")
	cmd += fmt.Sprintf(" && crontab temp_cron\nrm temp_cron")
	s.RunCommand(cmd)
}

type routerConfig struct {
	Addr     string `json:"addr"`
	Password string `json:"password"`
	Port     string `json:"port"`
}

func NewRunSSH() *SSHClass {
	buf, err := os.ReadFile("./config.json")
	if err != nil {
		log.Error(err.Error())
		f, _ := os.Create("./config.json")
		f.Close()
		return nil
	}

	rConfig := new(routerConfig)
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
