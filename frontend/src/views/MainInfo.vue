<script>
import MyMenu from '../components/Menu.vue'
import MyLogin from '../components/Login.vue'

export default {
  components: {
    MyMenu,
    MyLogin
  },
  name: 'MainInfo'
}
</script>

<template>
<div class="MainInfo">
  <MyMenu/>
  <div>
    <Card style="width:80%" id="login_card">
      <div style="text-align:center">
        <h1>配置上网账户</h1>
        <br v-for="index of 1" :id="index"/>
        <MyLogin/>
      </div>
    </Card>
  </div>
</div>
</template>

<style scoped>
.MainInfo {
  text-align: center;
  position: relative;
}
#login_card {
  text-align: center;
  margin: 5% auto;
  padding-bottom: 8px;
}
</style>
