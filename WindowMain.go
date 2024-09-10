//go:build windows

package main

import (
	"embed"
	"github.com/labstack/gommon/log"
	"os"

	"github.com/wailsapp/wails/v2"
	"github.com/wailsapp/wails/v2/pkg/options"
	"github.com/wailsapp/wails/v2/pkg/options/assetserver"

	"github.com/krxkgit/scut-router/SSHCommand"
)

//go:embed all:frontend/dist
var assets embed.FS

func main() {
	// Create an instance of the app structure
	app := NewApp()

	// 设置日志输出
	flog, err := os.Create("ScutLog.log")
	if err != nil {
		return
	}
	defer flog.Close()
	log.SetOutput(flog)

	f, err := os.Create("Output.txt")
	if err != nil {
		log.Error(err.Error())
		return
	}
	defer f.Close()
	f.WriteString("Startup")
	SSHObj := SSHCommand.NewRunSSH("")
	SSHObj.SetOut(f)

	SSHObj.RunCommand("touch test.txt") // 运行测试命令
	defer SSHObj.Close()                // 不关闭连接

	// Create application with options
	err = wails.Run(&options.App{
		Title:  "ScutRouter",
		Width:  1024,
		Height: 768,
		AssetServer: &assetserver.Options{
			Assets: assets,
		},
		BackgroundColour: &options.RGBA{R: 27, G: 38, B: 54, A: 1},
		OnStartup:        app.startup,
		Bind: []interface{}{
			app,
			SSHObj,
		},
	})

	if err != nil {
		println("Error:", err.Error())
	}
}
