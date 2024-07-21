package main

import "C"

import (
	"encoding/json"
	"github.com/krxkgit/scut-router/SSHCommand"
	"github.com/labstack/gommon/log"
	"os"
)

var CompatibleSSHObj *SSHCommand.SSHClass = nil
var flog, f *os.File

/**
 * 辅助函数： 判断文件是否存在
 */
func fileExists(filename string) bool {
	_, err := os.Stat(filename)
	if err == nil {
		// 文件存在
		return true
	}
	if os.IsNotExist(err) {
		// 文件不存在
		return false
	}
	// 其他错误
	return false
}

//export Init
func Init() {
	if CompatibleSSHObj == nil {
		outPath := "/sdcard/Download/Scut-Router"
		os.Chdir(outPath) // 改变当前工作目录

		if !fileExists(outPath) {
			err := os.MkdirAll(outPath, 0777)
			if err != nil {
				log.Error(err.Error())
			}
		}

		// 设置日志输出
		flog, err := os.Create("ScutLog.log")
		if err != nil {
			return
		}
		log.SetOutput(flog)

		f, err := os.Create("Output.txt")
		if err != nil {
			log.Error(err.Error())
			return
		}
		f.WriteString("Startup")

		configPath := "./config.json"
		if !fileExists(configPath) {
			fConfig, _ := os.Create(configPath)
			defer fConfig.Close()
			/**
			 * 若配置文件不存在，写入默认配置文件
			 */
			rConfig := SSHCommand.RouterConfig{
				Addr:     "192.168.0.101",
				Password: "KrxkKrxk",
				Port:     "22",
			}
			b, _ := json.Marshal(rConfig)
			fConfig.Write(b)
			fConfig.Close()

		}
		CompatibleSSHObj = SSHCommand.NewRunSSH()
		CompatibleSSHObj.SetOut(f)
	}
}

//export Destroy
func Destroy() {
	if CompatibleSSHObj != nil {
		CompatibleSSHObj.Close()
		flog.Close()
		f.Close()
	}
}

//export Reboot
func Reboot() {
	CompatibleSSHObj.RunReboot()
}

//export SetScutInfo
func SetScutInfo(username, password string) {
	CompatibleSSHObj.RunSetScutInfo(username, password)
}

//export SetAcInfo
func SetAcInfo(acIP, acName string) {
	CompatibleSSHObj.RunSetAcInfo(acIP, acName)
}

//export WiredLogin
func WiredLogin() {
	CompatibleSSHObj.RunWiredLogin()
}

//export WirelessLogin
func WirelessLogin() {
	CompatibleSSHObj.RunWirelessLogin()
}

//export SyncTime
func SyncTime() {
	CompatibleSSHObj.RunSyncTime()
}

//export AutoLogin
func AutoLogin() {
	CompatibleSSHObj.RunAutoLogin()
}

//export SetNetwork
func SetNetwork(ip string, dnsArr string, netmask, gateway string) {
	CompatibleSSHObj.RunSetNetwork(ip, dnsArr, netmask, gateway)
}
