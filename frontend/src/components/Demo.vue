<style>
.layout-base{
  border: 1px solid #d7dde4;
  background: #f5f7f9;
  position: relative;
  border-radius: 4px;
  color: white;
  text-align: left;
  overflow: hidden;
}
.layout-base > .ivu-layout {
  margin-bottom: 20px;
}
.layout-hide-text .layout-text{
  display: none;
}
.layout-header {
  text-align: center;
}
.layout-footer{
  background: #7CBCE9;
  color: white;
  text-align: right;
}
.layout-content{
  padding-top: 20px;
  background: white;
  min-height: 120px;
  line-height: 120px;
  color: black;
  text-align: center;
}
.layout iframe{
  width: 100%;
  border: 0;
}
.menu-item span{
  display: inline-block;
  overflow: hidden;
  width: 69px;
  text-overflow: ellipsis;
  white-space: nowrap;
  vertical-align: bottom;
  transition: width .2s ease .2s;
}
.menu-item i{
  transform: translateX(0px);
  transition: font-size .2s ease, transform .2s ease;
  vertical-align: middle;
  font-size: 16px;
}
.collapsed-menu span{
  width: 0px;
  transition: width .2s ease;
}
.collapsed-menu i{
  transform: translateX(5px);
  transition: font-size .2s ease .2s, transform .2s ease .2s;
  vertical-align: middle;
  font-size: 22px;
}
</style>
<template>
  <div class="layout-base">
    <Layout>
      <MyMenu/>
      <Header class="layout-header">
        <h1>欢迎使用校园网路由器尊享版</h1>
        <MyAvatar/>
      </Header>
      <Content class="layout-content">
        <h2>自由定制，真正属于你的校园网路由器</h2>
        <img src="../assets/Scut-Router.png"/>
      </Content>
      <Footer  class="layout-footer">
        Developed By Krxk.
      </Footer>
    </Layout>
  </div>
</template>
<script>
import MyMenu from './Menu.vue'
import MyAvatar from './Avatar.vue'

export default {
  components: {
    MyMenu,
    MyAvatar
  }
}
</script>
