<script>
import MyMenu from '../components/Menu.vue'
import Success from "../components/Success.vue"
import Fail from "../components/Fail.vue"
import {RunWiredLogin} from "../../wailsjs/go/SSHCommand/SSHClass";

export default {
  components: {
    MyMenu,
    Success,
    Fail
  },
  name: 'WiredLogin',
  data() {
    return {
      ConnectStatus: false,
      FailStatus: false
    }
  },
  methods: {
    TryConnect() {
      RunWiredLogin()
      this.ConnectStatus = true
      // this.FailStatus = true
    }
  }
}
</script>

<template>
  <div class="WiredLogin">
    <MyMenu/>
    <div class="WiredLogin">
      <Card style="width:320px" id="WiredCard">
        <div style="text-align:center">
          <h2>连接有线网</h2>
          <br/>
          <Success v-if="ConnectStatus"/>
          <Fail v-if="FailStatus"/>
          <Space wrap>
            <Button type="info" v-model="ConnectStatus" @click="TryConnect">连接</Button>
          </Space>
        </div>
      </Card>
    </div>
  </div>
</template>

<style scoped>
.WiredLogin {
  margin: 0 auto;
  text-align: center;
}
#WiredCard {
  margin: 5% auto;
}
</style>
