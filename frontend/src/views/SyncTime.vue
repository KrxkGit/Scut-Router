<script>
import MyMenu from '../components/Menu.vue'
import Success from "../components/Success.vue"
import Fail from "../components/Fail.vue"
import {RunSyncTime} from "../../wailsjs/go/SSHCommand/SSHClass";

export default {
  components: {
    MyMenu,
    Success,
    Fail
  },
  name: 'SyncTime',
  data() {
    return {
      SyncStatus: false,
      FailStatus: false
    }
  },
  methods: {
    TryConnect() {
      RunSyncTime()
      this.SyncStatus = true
      // this.FailStatus = true
    }
  }
}
</script>

<template>
  <div class="SyncTime">
    <MyMenu/>
    <div class="SyncTime">
      <Card style="width:320px" id="SyncTimeCard">
        <div style="text-align:center">
          <h2>同步路由器时间</h2>
          <br/>
          <Success v-if="SyncStatus"/>
          <Fail v-if="FailStatus"/>
          <Space wrap>
            <Button type="info" v-model="SyncStatus" @click="TryConnect">连接</Button>
          </Space>
        </div>
      </Card>
    </div>
  </div>
</template>

<style scoped>
.SyncTime {
  margin: 0 auto;
  text-align: center;
}
#SyncTimeCard {
  margin: 5% auto;
}
</style>
