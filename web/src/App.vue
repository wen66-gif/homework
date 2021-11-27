<template>
  <div id="app">
    <Header/>
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
    overflow: auto;
    background-color: #F1F3F6;
    width: 100%;
    height: 100%;
  }
</style>
