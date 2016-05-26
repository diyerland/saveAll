
persistence.store.websql.config(persistence, 'daohang', '导航', 5 * 1024 * 1024);
var Tag = persistence.define('Tag', {
  name: "TEXT",
});

var Category = persistence.define('Category', {
  name: "TEXT"
});

var Website = persistence.define('Website',{
  name: "TEXT",
  tag: "TEXT",
  category: "TEXT"
});
Category.index(['name'],{unique:true});

persistence.schemaSync();

var c = new Category({name:"计算机"});
persistence.add(c);
c = new Category({name:"编程乐趣"});
persistence.add(c);
persistence.flush();


var allCategory = Category.all();
  allCategory.list(null,function(results){
    results.forEach(function(r){
    //document.write('<button class="btn btn-primary">');
    //document.write(r.name);
    //document.write('</button>');
    
  });
});

//document.write('1.....');

var daohangWidget = React.createClass({

    render:function(){
        return React.DOM.p(null,"Daohang React");
    }
});

//var daohangWidgetFactory = React.createFactory(daohangWidget);
//ReactDOM.render(daohangWidgetFactory({}),document.getElementById('container'));

//$.get("https://raw.githubusercontent.com/google/guava/master/README.md",function(res){
//    console.log(res);
//    document.getElementById('container_grab').innerHTML = res;
//});

$("button").on('click',function (){

    document.getElementById('container').innerHTML = $(this).text();
});
//console.log("Tabs.getCurrent");
chrome.tabs.query({active: true, currentWindow: true}, function(tabs) {
    console.log(tabs[0].url);
    $("#input-website-add").val(tabs[0].url);
});

