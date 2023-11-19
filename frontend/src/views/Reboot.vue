<script>
import MyMenu from '../components/Menu.vue'
import Success from '../components/Success.vue'
import Fail from "../components/Fail.vue"
import { RunReboot } from "../../wailsjs/go/SSHCommand/SSHClass"

export default {
  components: {
    MyMenu,
    Success,
    Fail
  },
  name: 'Reboot',
  data() {
    return {
      ConnectStatus: false,
      FailStatus: false
    }
  },
  methods: {
    TryReboot() {
      RunReboot()
      this.ConnectStatus = true
      // this.FailStatus = true
    }
  }
}
</script>

<template>
  <div class="Reboot">
    <MyMenu/>
    <div class="Reboot">
      <Card style="width:320px" id="RebootCard">
        <div style="text-align:center">
          <h2>重启路由器</h2>
          <br/>
          <Success v-if="ConnectStatus"/>
          <Fail v-if="FailStatus"/>
          <Space wrap>
            <Button type="info" v-model="ConnectStatus" @click="TryReboot">重启</Button>
          </Space>
        </div>
      </Card>
    </div>
  </div>
</template>

<style scoped>
.Reboot {
  margin: 0 auto;
  text-align: center;
}
#RebootCard {
  margin: 5% auto;
}
</style>
