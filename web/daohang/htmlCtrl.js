angular.module('htmlCtrlApp', [])
  .controller('HtmlController', function HtmlController($scope,$http) {
    var ctrl = this;
    // window.localStorage.load("local-save");
    window.localStorage.getItem("server");
    ctrl.server =  window.localStorage.server;

  ctrl.saveServer = function() {
      window.localStorage.server = ctrl.server;
      storage.setItem("server", ctrl.server);
      // window.localStorage.save("local-save");
    };

 ctrl.websites = [];
 
 ctrl.getWebsites = function(){
  $http.get(ctrl.server+'/Website'+'?sort=good')
    .success(function(data, status, headers, config) {
      //加载成功之后做一些事
      ctrl.websites = data;
      if(ctrl.websites.count > 10000){
        alert("您收藏了太多的网站，请清理一下。暂不支持10000+的网站");
      }
    }).error(function(data, status, headers, config) {
      //处理错误
    });
 };
 ctrl.getWebsites();
 ctrl.addWebsite = function(name,url,good){
  var website = {"good":"0"};
  website.name = name;
  console.log(website);
  website.url = url;
  console.log(website);
  website.good = good;
  console.log(website);
  $http.post(ctrl.server+'/Website',JSON.stringify(website))
    .success(function(data, status, headers, config) {
      //加载成功之后做一些事
      ctrl.websites.push(data);

        // alert("增加网站成功");
      
    }).error(function(data, status, headers, config) {
      //处理错误
        // alert("增加网站失败");
    });
 };
 ctrl.findWebsite = function(url){
  for (var i=0;i<ctrl.websites.length;i++){
    var website=ctrl.websites[i];
    console.log(website.url);
    if(website.url.indexOf(url)!=-1){
      console.log(url);
      return website;
    }
  }
  console.log("no website found");
  return null;
 };
 ctrl.updateWebsite = function(website){
  console.log(JSON.stringify(website));
  $http.post(ctrl.server+'/Website/'+website._id,JSON.stringify(website))
    .success(function(data, status, headers, config) {
      
        // alert("更新网站成功");
    }).error(function(data, status, headers, config) {
      //处理错误
        // alert("更新网站失败");
    });
 };
 ctrl.goodWebsite =  function(){
  chrome.tabs.query({ currentWindow: true, active: true }, function(tab){ 
      console.log(tab[0].title); 
      console.log(tab[0].url); 
      var a =  document.createElement('a');  
      a.href = tab[0].url;  
      //alert(a.hostname);
      var website = ctrl.findWebsite(a.hostname);
      if(website == null){
          ctrl.addWebsite(tab[0].title,a.hostname,1);
          ctrl.addHtml(tab[0].title,tab[0].url,1);
      }else{
        website.url = a.hostname;
        website.good ++;
        ctrl.updateWebsite(website);
        ctrl.addHtml(tab[0].title,tab[0].url,1);
      }
  });
 };

 ctrl.addHtml = function(name,url,good){
  var website = {"good":"0"};
  website.name = name;
  console.log(website);
  website.url = url;
  console.log(website);
  $http.post(ctrl.server+'/Html',JSON.stringify(website))
    .success(function(data, status, headers, config) {
      //加载成功之后做一些事

        // alert("增加网站成功");
      
    }).error(function(data, status, headers, config) {
      //处理错误
        // alert("增加网站失败");
    });
 };


    // ctrl.addTodo = function() {
    //   ctrl.todos.push({text:ctrl.todoText, done:false});
    //   ctrl.todoText = '';
    // };
 
    // ctrl.remaining = function() {
    //   var count = 0;
      
    //   return count;
    // };
 
    // ctrl.archive = function() {
    //   var oldTodos = ctrl.todos;
    //   ctrl.todos = [];
    //   angular.forEach(oldTodos, function(todo) {
    //     if (!todo.done) ctrl.todos.push(todo);
    //   });
    // };
});