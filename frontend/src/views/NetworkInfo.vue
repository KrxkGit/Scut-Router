<script>
import { ref } from 'vue'
import MyMenu from '../components/Menu.vue'
import { RunSetNetwork } from '../../wailsjs/go/SSHCommand/SSHClass'

function PackDns(dns1, dns2) {
  // eslint-disable-next-line no-array-constructor
  const dnsArr = `${dns1} ${dns2}`
  return dnsArr
}
export default {
  data() {
    return {
      IP: '',
      DNS1: '',
      DNS2: '',
      netmask: '',
      gateway: '',
    }
  },
  components: {
    MyMenu
  },
  methods: {
    OnSubmit() {
      RunSetNetwork(this.IP, PackDns(this.DNS1, this.DNS2), this.netmask, this.gateway)
      // alert(`${this.IP} | ${this.DNS1} | ${this.DNS2} | ${this.netmask} | ${this.gateway}`)
      alert('完成')
    }
  },
  name: 'NetworkInfo'
}
</script>

<template>
  <div class="NetWorkInfo">
    <MyMenu/>
    <div>
      <Card style="width:80%" id="net_card">
        <div style="text-align:center">
          <h1>配置网络接口</h1>
          <br v-for="index of 1" :id="index"/>
          <h3>Scut Interface</h3>
          <br/>
          <Input v-model="IP" placeholder="请输入Scut IP" style="width: 200px" clearable="true"/>
          <br v-for="index of 2" :id="index"/>
          <Input v-model="netmask" placeholder="请输入Scut NetMask" style="width: 200px" clearable="true"/>
          <br v-for="index of 2" :id="index"/>
          <Input v-model="gateway" placeholder="请输入Scut GateWay" style="width: 200px" clearable="true"/>
          <br v-for="index of 2" :id="index"/>
          <Input v-model="DNS1" placeholder="请输入Scut DNS1" style="width: 200px" clearable="true"/>
          <br v-for="index of 2" :id="index"/>
          <Input v-model="DNS2" placeholder="请输入Scut DNS2" style="width: 200px" clearable="true"/>
          <br v-for="index of 2" :id="index"/>
          <Button type="success" @click="OnSubmit">保存并应用</Button>
        </div>
      </Card>
    </div>
  </div>
</template>

<style scoped>
.NetWorkInfo {
  text-align: center;
  position: relative;
}

#net_card {
  margin: 5% auto;
}
</style>
