<template>
  <Menu mode="horizontal" :theme="theme" active-name="1">
    <MenuItem name="1" @click="$router.push('/')">
      <Icon type="ios-home" />
      主页
    </MenuItem>
    <Submenu name="2">
      <template #title>
        <Icon type="md-settings" />
        配置信息
      </template>
      <MenuGroup title="账号密码">
        <MenuItem name="2-1" @click="$router.push('/MainInfo')">账号密码</MenuItem>
      </MenuGroup>
      <MenuGroup title="网络接口">
        <MenuItem name="2-2" @click="$router.push('/NetworkInfo')">上网接口</MenuItem>
      </MenuGroup>
      <MenuGroup title="认证服务器">
        <MenuItem name="2-3" @click="$router.push('/ACInfo')">认证服务器</MenuItem>
      </MenuGroup>
    </Submenu>
    <Submenu name="3">
      <template #title>
        <Icon type="ios-navigate" />
        系统功能
      </template>
      <MenuGroup title="有线登录">
        <MenuItem name="3-1" @click="$router.push('/WiredLogin')">有线登录</MenuItem>
      </MenuGroup>
      <MenuGroup title="无线登录">
        <MenuItem name="3-2" @click="$router.push('/WirelessLogin')">无线登录</MenuItem>
      </MenuGroup>
      <MenuGroup title="时间同步">
        <MenuItem name="3-3" @click="$router.push('/SyncTime')">时间同步</MenuItem>
      </MenuGroup>
      <MenuGroup title="重启设备">
        <MenuItem name="3-4" @click="$router.push('/RebootDevice')">重启路由器</MenuItem>
      </MenuGroup>
    </Submenu>
    <Submenu name="4">
      <template #title>
        <Icon type="md-help-circle" />
        关于
      </template>
      <MenuItem name="4-1" @click="OpenScutRouterDoc">说明文档</MenuItem>
      <MenuItem name="4-2" @click="OpenKrxkGithub">Github</MenuItem>
    </Submenu>
  </Menu>
  <RadioGroup id="MyMenuTheme" v-model="theme">
    <Radio label="light">亮色</Radio>
    <Radio label="dark">暗色</Radio>
    <Radio label="primary">基础</Radio>
  </RadioGroup>
</template>
<script>
function OpenScutRouterDoc() {
  window.open('https://krxkgo.top/scut-router.html')
}

function OpenKrxkGithub() {
  window.open('https://github.com/KrxkGit/')
}
export default {
  data() {
    return {
      theme: 'light'
    }
  },
  name: 'MyMenu',
  methods: {
    OpenScutRouterDoc,
    OpenKrxkGithub
  }
}
</script>

<style>
#MyMenuTheme {
  color: #5b6270;
  float: right;
  text-align: right;
}
</style>
