//网页生成工厂
//一个RootElement是node_gen_container
//
//方式一 创建文本文档 
  function addText(var parent){ 
   //1利用createTextNode()创建一个文本对象 
   var text=document.createTextNode("这是修改的，创建的文档"); 
   //2获取div对象 
   var node1=document.getElementById(parent); 
   //添加成div对象的孩子 
   node1.appendChild(text); 
  } 
    
  function addInput(var parent){ 
   //1,利用createElement（）创建一个标签对象 
   var nn=document.createElement("input"); 
   nn.type="button" 
   nn.value="创建的按钮"; 
   nn.target="_blank"; 
   //2,获得div对象 
   var node2=document.getElementById(parent); 
   //添加成div对象的孩子 
   node2.appendChild(nn); 
  } 
    
  //直接利用容器标签中的一个属性：innerHTML-----本质上改该标签容器中的“html代码”，不是我们认为的对象树的操作 
  function addNode3(){ 
    var mm=document.getElementById("div_id3"); 
    mm.innerHTML="<a href='http://www.baidu.com'><input type='button' value='新建的按钮'></a>"; 
      
  } 
//BBBBBB-------删   
  //删除节点 使用 removeNode 和 removeChild 从元素上删除子结点两种方法，通常采用第二种方法 
  function removenode(){ 
   var node =document.getElementById("div_id4"); 
//   alert(node.nodeName);//DIV 
//  自杀式 node.removeNode(true); //removeNode 从文档层次中删除对象。ie可以出现现象，一般不采用自杀式 
   node.parentNode.removeChild(node);////通过父节点去删除它的孩子,一般常用 
   alert("aa"); 
  } 
  //替换 没有保留替换的那个 
  function remove2(){ 
   var node1 =document.getElementById("div_id1"); 
   var node2 =document.getElementById("div_id2"); 
//   node1.replaceNode(node2);//自杀式不通用 
////通过父节点去替换它的孩子：用node1去替换node2 
   node1.parentNode.replaceChild(node1,node2);//object.replaceChild(oNewNode, oChildNode) 
  } 
  function clone(){ 
   var node1 =document.getElementById("div_id1"); 
   var node2 =document.getElementById("div_id2"); 
   var node1_2=node1.cloneNode(true);//false只能clone基本的，不会clone下面的其他子节点 
   //克隆一个对象,默认参数为false。参数为true时，连子节点一起克隆 
   node1.parentNode.replaceChild(node1_2,node2); 
  } 
