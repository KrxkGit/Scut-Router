<script>
import MyMenu from '../components/Menu.vue'

const ACList = [] // 复合型const变量只确保地址不变而非值不变，故可修改
for (let i = 1; i < 20; i += 1) {
  const s = `WX6108E-slot${i}-AC`
  ACList.push({ value: s, label: s })
}

export default {
  data() {
    return {
      ACList,
      AcName: '',
      AcIp: ''
    }
  },
  components: {
    MyMenu
  },
  methods: {
    OnSubmit() {
      alert(`Wlan_ac_name: ${this.AcName}\nWlan_ac_ip: ${this.AcIp}`)
    }
  },
  name: 'ACInfo'
}
</script>

<template>
  <div class="ACInfo">
    <MyMenu/>
    <div>
      <Card style="width:80%" id="ac_card">
        <div style="text-align:center">
          <h1>配置认证服务器</h1>
          <br v-for="index of 1" :id="index"/>
          <h3>Wlan_ac_name</h3>
          <br/>
          <Select v-model="AcName" style="width:200px">
            <Option v-for="item in ACList" :value="item.value" :key="item.value">
              {{ item.label }}
            </Option>
          </Select>
          <br v-for="index of 3" :id="index"/>
          <h3>Wlan_ac_ip</h3>
          <br/>
          <Input v-model="AcIp" placeholder="请输入认证服务器 IP " style="width: 200px" clearable="true"/>
          <br v-for="index of 2" :id="index"/>
          <Button type="success" @click="OnSubmit">保存并应用</Button>
        </div>
      </Card>
    </div>
  </div>
</template>

<style scoped>
.ACInfo {
  text-align: center;
  position: relative;
}

#ac_card {
  margin: 5% auto;
}
</style>
