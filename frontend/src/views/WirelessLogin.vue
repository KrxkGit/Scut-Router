<script>
import MyMenu from '../components/Menu.vue'
import Success from '../components/Success.vue'
import Fail from "../components/Fail.vue"
import {RunWirelessLogin} from "../../wailsjs/go/SSHCommand/SSHClass";

export default {
  components: {
    MyMenu,
    Success,
    Fail
  },
  name: 'WirelessLogin',
  data() {
    return {
      ConnectStatus: false,
      FailStatus: false
    }
  },
  methods: {
    TryConnect() {
      RunWirelessLogin()
      this.ConnectStatus = true
      // this.FailStatus = true
    }
  }
}
</script>

<template>
  <div class="WirelessLogin">
    <MyMenu/>
    <div class="WirelessLogin">
      <Card style="width:320px" id="WirelessCard">
        <div style="text-align:center">
          <h2>连接无线网</h2>
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
.WirelessLogin {
  margin: 0 auto;
  text-align: center;
}
#WirelessCard {
  margin: 5% auto;
}
</style>
