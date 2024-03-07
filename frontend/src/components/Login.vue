<template>
  <div class="demo-login">
    <Login @on-submit="handleSubmit">
      <UserName name="username" type="number"/>
      <Password name="password" />
      <RadioGroup v-model="AccountType">
        <Radio label="本科生">
          <Icon type="ios-clock" />
          <span>本科生</span>
        </Radio>
        <Radio label="研究生">
          <Icon type="ios-globe-outline" />
          <span>研究生</span>
        </Radio>
      </RadioGroup>
      <br v-for="index of 2" :id="index"/>
      <Submit>
        保存并应用
      </Submit>
    </Login>
  </div>
</template>
<script>
import {RunAutoLogin, CancelAutoLogin, RunSetScutInfo} from "../../wailsjs/go/SSHCommand/SSHClass";

export default {
  data() {
    return {
      AccountType: ''
    }
  },
  methods: {
    handleSubmit(valid, { username, password }) {
      if(valid) {
        RunSetScutInfo(username, password)
        if(this.AccountType == "本科生") {
          RunAutoLogin()
        } else {
          CancelAutoLogin()
        }
        alert('完成')
      }
    },
  },
  name: 'MyLogin'
}
</script>
<style>
.demo-login{
  width: 400px;
  margin: 0 auto !important;
}
#UseTimeScript {
  display: flex;
  text-align: center;
  margin: 0 auto;
}
</style>
