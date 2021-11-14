<template>
  <div id="app">
    <div>
      <Header/>
    </div>
    <router-view/>
  </div>
</template>

<script>
  import Header from "./components/Header";

  export default {
    components:{
      Header
    }
  }
</script>

<style>
  #app {
    background-color: #DDDDDD;
    width: 100%;
    height: 100%;
  }
</style>
