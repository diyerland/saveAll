angular.module('htmlCtrlApp', [])
  .controller('HtmlController', function() {
    var ctrl = this;
    ctrl.database =  window.localStorage.database;

  ctrl.connect = function() {
      
    };

    ctrl.todos = [
      {text:'learn angular', done:true},
      {text:'build an angular app', done:false}];
 
    ctrl.addTodo = function() {
      ctrl.todos.push({text:ctrl.todoText, done:false});
      ctrl.todoText = '';
    };
 
    ctrl.remaining = function() {
      var count = 0;
      angular.forEach(ctrl.todos, function(todo) {
        count += todo.done ? 0 : 1;
      });
      return count;
    };
 
    ctrl.archive = function() {
      var oldTodos = ctrl.todos;
      ctrl.todos = [];
      angular.forEach(oldTodos, function(todo) {
        if (!todo.done) ctrl.todos.push(todo);
      });
    };
  });
