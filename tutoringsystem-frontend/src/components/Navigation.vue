<template>
  <div v-if='this.$route !== "/Login" && this.$route !== "/Signup"'>
    <router-link to="/Hello">Home</router-link>
    <router-link to="/SessionList">Sessions</router-link>
  </div>
</template>