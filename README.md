# README

## ❔关于

本项目是Scut校园网路由器管理客户端，基于 Vue3 + go1.21 + wails2.6。

## ▶编译前端

frontend目录是一个vue3项目，请先运行

```
npm install
```

安装前端相关依赖。

然后运行

```
npm build
```

构建前端。

## ▶编译应用程序

Wails 将利用*Vite*构建前端，故需要利用npm安装**Vite**，接着在MINGW64(安装git时会自动安装)运行

```shell
wails dev
```

完成首次环境配置。

此后，可通过

```sh
wails build
```

完成应用程序构建。

## ❗注意

若出现首页Avatar无法读取的情况，可先删除

> frontend/dist/MyAvatar.jpg

然后重新构建。

## ▶编译 Android

### ▶编译 Golang 跨语言包

> cd Compatibility

> ./build_android.sh

### ▶编译为 APK

使用 Android Studio 打开 Android 文件夹 项目编译即可。
