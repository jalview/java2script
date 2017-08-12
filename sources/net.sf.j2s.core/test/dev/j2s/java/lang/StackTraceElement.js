Clazz.load (null, "java.lang.StackTraceElement", ["java.lang.NullPointerException", "$.StringBuilder"], function () {
;
(function(){var C$ = Clazz.decorateAsClass (function () {
Clazz.newInstance$ (this, arguments);
}, java.lang, "StackTraceElement", null, java.io.Serializable);

Clazz.newMethod$(C$, '$init$', function () {
this.declaringClass = null;
this.methodName = null;
this.fileName = null;
this.lineNumber = 0;
}, 1);

Clazz.newMethod$ (C$, 'construct$S$S$S$I', function (cls, method, file, line) {
if (cls == null || method == null) {
throw Clazz.$new(NullPointerException.construct);
}this.declaringClass = cls;
this.methodName = method;
this.fileName = file;
this.lineNumber = line;
}, 1);

Clazz.newMethod$ (C$, 'construct', function () {
}, 1);

Clazz.newMethod$ (C$, 'equals$O', function (obj) {
if (!(Clazz.instanceOf(obj, StackTraceElement))) {
return false;
}var castObj = obj;
if ((this.methodName == null) || (castObj.methodName == null)) {
return false;
}if (!this.getMethodName ().equals$O (castObj.getMethodName ())) {
return false;
}if (!this.getClassName ().equals$O (castObj.getClassName ())) {
return false;
}var localFileName = this.getFileName ();
if (localFileName == null) {
if (castObj.getFileName () != null) {
return false;
}} else {
if (!localFileName.equals$O (castObj.getFileName ())) {
return false;
}}if (this.getLineNumber () != castObj.getLineNumber ()) {
return false;
}return true;
});

Clazz.newMethod$ (C$, 'getClassName', function () {
return (this.declaringClass == null) ? "<unknown class>" : this.declaringClass;
});

Clazz.newMethod$ (C$, 'getFileName', function () {
return this.fileName;
});

Clazz.newMethod$ (C$, 'getLineNumber', function () {
return this.lineNumber;
});

Clazz.newMethod$ (C$, 'getMethodName', function () {
return (this.methodName == null) ? "<unknown method>" : this.methodName;
});

Clazz.newMethod$ (C$, 'hashCode', function () {
if (this.methodName == null) {
return 0;
}return this.methodName.hashCode () ^ this.declaringClass.hashCode ();
});

Clazz.newMethod$ (C$, 'isNativeMethod', function () {
return this.lineNumber == -2;
});

Clazz.newMethod$ (C$, 'toString', function () {
var buf = Clazz.$new(StringBuilder.construct$I,[80]);
buf.append$S (this.getClassName ());
buf.append$C ('.');
buf.append$S (this.getMethodName ());
if (this.isNativeMethod ()) {
buf.append$S ("(Native Method)");
} else {
var fName = this.getFileName ();
if (fName == null) {
buf.append$S ("(Unknown Source)");
} else {
var lineNum = this.getLineNumber ();
buf.append$C ('(');
buf.append$S (fName);
if (lineNum >= 0) {
buf.append$C (':');
buf.append$I (lineNum);
}buf.append$C (')');
}}return buf.toString ();
});
})()
});

//Created 2017-08-12 07:32:16
