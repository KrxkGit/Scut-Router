<template>
  <Row>
    <Col span="12">
      <Space direction="vertical">
        <MyLogin/>
      </Space>
    </Col>
    <Col span="12">
      <Space direction="vertical" type="flex">
      </Space>
    </Col>
  </Row>
</template>
<script>
import MyLogin from './Login.vue'

export default {
  name: 'MySpace',
  components: {
    MyLogin
  }
}
</script>
