/* http://j2s.sf.net/ */Clazz.load(["java.lang.Comparable","$.Number"],"java.lang.Integer",null,function(){
java.lang.Integer=Integer=function(){
Clazz.instantialize(this,arguments);
};
Clazz.decorateAsType(Integer,"Integer",Number,Comparable);
Integer.prototype.valueOf=function(){return 0;};
Integer.prototype.toString=function(){
return""+this.valueOf();
};
Clazz.makeConstructor(Integer,
function(){
this.valueOf=function(){
return 0;
};
});
Clazz.makeConstructor(Integer,
function(value){
var v=Math.round(value)&0xffffffff;
this.valueOf=function(){
return v;
};
},"Number");
Clazz.makeConstructor(Integer,
function(s){
var value=Integer.parseInt(s,10);
this.valueOf=function(){
return value;
};
},"String");
Integer.serialVersionUID=Integer.prototype.serialVersionUID=1360826667806852920;
Integer.MIN_VALUE=Integer.prototype.MIN_VALUE=-0x80000000;
Integer.MAX_VALUE=Integer.prototype.MAX_VALUE=0x7fffffff;

Clazz.defineMethod(Integer,"parseInt",
function(s,radix){
if(s==null){
throw new NumberFormatException("int null");
}if(radix<2){
throw new NumberFormatException("radix "+radix+" less than Character.MIN_RADIX");
}if(radix>36){
throw new NumberFormatException("radix "+radix+" greater than Character.MAX_RADIX");
}
return parseInt(s,radix);
},"String, Number");
Integer.parseInt=Integer.prototype.parseInt;
Clazz.defineMethod(Integer,"parseInt",
function(s){
return Integer.parseInt(s,10);
},"String");
Integer.parseInt=Integer.prototype.parseInt;

Clazz.defineMethod(Integer,"equals",
function(s){
if(s==null||!Clazz.instanceOf(s,Integer)){
return false;
}
return s.valueOf()==this.valueOf();
},"Object");
Integer.toHexString=Integer.prototype.toHexString=function(i){
return i.toString(16);
};
Integer.toOctalString=Integer.prototype.toOctalString=function(i){
return i.toString(8);
};
Integer.toBinaryString=Integer.prototype.toBinaryString=function(i){
return i.toString(2);
};
});