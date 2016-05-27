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
  var website ;
  website.name =name;
  website.url = url;
  website.good = good;
  $http.post(ctrl.server+'/Website',JSON.stringify(website))
    .success(function(data, status, headers, config) {
      //加载成功之后做一些事
      ctrl.websites.push(data);

        alert("增加网站成功");
      
    }).error(function(data, status, headers, config) {
      //处理错误
        alert("增加网站失败");
    });
 };
 ctrl.findWebsite = function(url){
  for (var i=0;i<ctrl.websites.length;i++){
    var website=ctrl.websites[i];
    if(website.url.indexOf(url)!=-1){
      return website;
    }
  }
  return null;
 };
 ctrl.updateWebsite = function(website){
  $http.post(ctrl.server+'/Website',JSON.stringify(website))
    .success(function(data, status, headers, config) {
      
        alert("更新网站成功");
    }).error(function(data, status, headers, config) {
      //处理错误
        alert("更新网站失败");
    });
 };
 ctrl.goodWebsite =  function(){
  chrome.tabs.getCurrent(function(tab){ 
      console.log(tab.title); 
      console.log(tab.url); 
      var a =  document.createElement('a');  
      a.href = tab.url;  
      var website = ctrl.findWebsite(a.href);
      if(website == null){
          ctrl.addWebsite(tab.title,tab.url,1);
      }else{
        website.good ++;
        ctrl.updateWebsite(website);
      }
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