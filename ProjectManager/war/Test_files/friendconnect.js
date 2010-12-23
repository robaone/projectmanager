window.google = window["google"] || {};google.friendconnect = google.friendconnect_ || {};if (!window['__ps_loaded__']) {/*http://www-a-fc-opensocial.googleusercontent.com/gadgets/js/rpc.js?c=1*/
var gadgets=gadgets||{},shindig=shindig||{},osapi=osapi||{};
;
gadgets.config=function(){var A={};
var B;
return{register:function(E,D,C){var F=A[E];
if(!F){F=[];
A[E]=F
}F.push({validators:D||{},callback:C})
},get:function(C){if(C){return B[C]||{}
}return B
},init:function(E,L){B=E;
for(var C in A){if(A.hasOwnProperty(C)){var D=A[C],I=E[C];
for(var H=0,G=D.length;
H<G;
++H){var J=D[H];
if(I&&!L){var F=J.validators;
for(var K in F){if(F.hasOwnProperty(K)){if(!F[K](I[K])){throw new Error('Invalid config value "'+I[K]+'" for parameter "'+K+'" in component "'+C+'"')
}}}}if(J.callback){J.callback(E)
}}}}},EnumValidator:function(F){var E=[];
if(arguments.length>1){for(var D=0,C;
(C=arguments[D]);
++D){E.push(C)
}}else{E=F
}return function(H){for(var G=0,I;
(I=E[G]);
++G){if(H===E[G]){return true
}}return false
}
},RegExValidator:function(C){return function(D){return C.test(D)
}
},ExistsValidator:function(C){return typeof C!=="undefined"
},NonEmptyStringValidator:function(C){return typeof C==="string"&&C.length>0
},BooleanValidator:function(C){return typeof C==="boolean"
},LikeValidator:function(C){return function(E){for(var F in C){if(C.hasOwnProperty(F)){var D=C[F];
if(!D(E[F])){return false
}}}return true
}
}}
}();;
gadgets.config.isGadget=false;
gadgets.config.isContainer=true;;
gadgets.log=(function(){var E=1;
var A=2;
var F=3;
var C=4;
var D=function(I){B(E,I)
};
gadgets.warn=function(I){B(A,I)
};
gadgets.error=function(I){B(F,I)
};
gadgets.setLogLevel=function(I){H=I
};
function B(J,I){if(J<H||!G){return 
}if(J===A&&G.warn){G.warn(I)
}else{if(J===F&&G.error){G.error(I)
}else{if(G.log){G.log(I)
}}}}D.INFO=E;
D.WARNING=A;
D.NONE=C;
var H=E;
var G=window.console?window.console:window.opera?window.opera.postError:undefined;
return D
})();;
var tamings___=tamings___||[];
tamings___.push(function(A){___.grantRead(gadgets.log,"INFO");
___.grantRead(gadgets.log,"WARNING");
___.grantRead(gadgets.log,"ERROR");
___.grantRead(gadgets.log,"NONE");
caja___.whitelistFuncs([[gadgets,"log"],[gadgets,"warn"],[gadgets,"error"],[gadgets,"setLogLevel"]])
});;
if(window.JSON&&window.JSON.parse&&window.JSON.stringify){gadgets.json=(function(){var A=/___$/;
return{parse:function(C){try{return window.JSON.parse(C)
}catch(B){return false
}},stringify:function(C){try{return window.JSON.stringify(C,function(E,D){return !A.test(E)?D:null
})
}catch(B){return null
}}}
})()
}else{gadgets.json=function(){function f(n){return n<10?"0"+n:n
}Date.prototype.toJSON=function(){return[this.getUTCFullYear(),"-",f(this.getUTCMonth()+1),"-",f(this.getUTCDate()),"T",f(this.getUTCHours()),":",f(this.getUTCMinutes()),":",f(this.getUTCSeconds()),"Z"].join("")
};
var m={"\b":"\\b","\t":"\\t","\n":"\\n","\f":"\\f","\r":"\\r",'"':'\\"',"\\":"\\\\"};
function stringify(value){var a,i,k,l,r=/["\\\x00-\x1f\x7f-\x9f]/g,v;
switch(typeof value){case"string":return r.test(value)?'"'+value.replace(r,function(a){var c=m[a];
if(c){return c
}c=a.charCodeAt();
return"\\u00"+Math.floor(c/16).toString(16)+(c%16).toString(16)
})+'"':'"'+value+'"';
case"number":return isFinite(value)?String(value):"null";
case"boolean":case"null":return String(value);
case"object":if(!value){return"null"
}a=[];
if(typeof value.length==="number"&&!value.propertyIsEnumerable("length")){l=value.length;
for(i=0;
i<l;
i+=1){a.push(stringify(value[i])||"null")
}return"["+a.join(",")+"]"
}for(k in value){if(k.match("___$")){continue
}if(value.hasOwnProperty(k)){if(typeof k==="string"){v=stringify(value[k]);
if(v){a.push(stringify(k)+":"+v)
}}}}return"{"+a.join(",")+"}"
}return""
}return{stringify:stringify,parse:function(text){if(/^[\],:{}\s]*$/.test(text.replace(/\\["\\\/b-u]/g,"@").replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g,"]").replace(/(?:^|:|,)(?:\s*\[)+/g,""))){return eval("("+text+")")
}return false
}}
}()
}gadgets.json.flatten=function(C){var D={};
if(C===null||C===undefined){return D
}for(var A in C){if(C.hasOwnProperty(A)){var B=C[A];
if(null===B||undefined===B){continue
}D[A]=(typeof B==="string")?B:gadgets.json.stringify(B)
}}return D
};;
var tamings___=tamings___||[];
tamings___.push(function(A){___.tamesTo(gadgets.json.stringify,safeJSON.stringify);
___.tamesTo(gadgets.json.parse,safeJSON.parse)
});;
gadgets.util=function(){function G(K){var L;
var I=K.indexOf("?");
var J=K.indexOf("#");
if(J===-1){L=K.substr(I+1)
}else{L=[K.substr(I+1,J-I-1),"&",K.substr(J+1)].join("")
}return L.split("&")
}var E=null;
var D={};
var C={};
var F=[];
var A={0:false,10:true,13:true,34:true,39:true,60:true,62:true,92:true,8232:true,8233:true};
function B(I,J){return String.fromCharCode(J)
}function H(I){D=I["core.util"]||{}
}if(gadgets.config){gadgets.config.register("core.util",null,H)
}return{getUrlParameters:function(R){var J=typeof R==="undefined";
if(E!==null&&J){return E
}var N={};
var K=G(R||document.location.href);
var P=window.decodeURIComponent?decodeURIComponent:unescape;
for(var M=0,L=K.length;
M<L;
++M){var O=K[M].indexOf("=");
if(O===-1){continue
}var I=K[M].substring(0,O);
var Q=K[M].substring(O+1);
Q=Q.replace(/\+/g," ");
N[I]=P(Q)
}if(J){E=N
}return N
},makeClosure:function(L,N,M){var K=[];
for(var J=2,I=arguments.length;
J<I;
++J){K.push(arguments[J])
}return function(){var O=K.slice();
for(var Q=0,P=arguments.length;
Q<P;
++Q){O.push(arguments[Q])
}return N.apply(L,O)
}
},makeEnum:function(J){var K,I,L={};
for(K=0;
(I=J[K]);
++K){L[I]=I
}return L
},getFeatureParameters:function(I){return typeof D[I]==="undefined"?null:D[I]
},hasFeature:function(I){return typeof D[I]!=="undefined"
},getServices:function(){return C
},registerOnLoadHandler:function(I){F.push(I)
},runOnLoadHandlers:function(){for(var J=0,I=F.length;
J<I;
++J){F[J]()
}},escape:function(I,M){if(!I){return I
}else{if(typeof I==="string"){return gadgets.util.escapeString(I)
}else{if(typeof I==="array"){for(var L=0,J=I.length;
L<J;
++L){I[L]=gadgets.util.escape(I[L])
}}else{if(typeof I==="object"&&M){var K={};
for(var N in I){if(I.hasOwnProperty(N)){K[gadgets.util.escapeString(N)]=gadgets.util.escape(I[N],true)
}}return K
}}}}return I
},escapeString:function(M){if(!M){return M
}var J=[],L,N;
for(var K=0,I=M.length;
K<I;
++K){L=M.charCodeAt(K);
N=A[L];
if(N===true){J.push("&#",L,";")
}else{if(N!==false){J.push(M.charAt(K))
}}}return J.join("")
},unescapeString:function(I){if(!I){return I
}return I.replace(/&#([0-9]+);/g,B)
},attachBrowserEvent:function(K,J,L,I){if(typeof K.addEventListener!="undefined"){K.addEventListener(J,L,I)
}else{if(typeof K.attachEvent!="undefined"){K.attachEvent("on"+J,L)
}else{gadgets.warn("cannot attachBrowserEvent: "+J)
}}},removeBrowserEvent:function(K,J,L,I){if(K.removeEventListener){K.removeEventListener(J,L,I)
}else{if(K.detachEvent){K.detachEvent("on"+J,L)
}else{gadgets.warn("cannot removeBrowserEvent: "+J)
}}}}
}();
gadgets.util.getUrlParameters();;
var tamings___=tamings___||[];
tamings___.push(function(A){caja___.whitelistFuncs([[gadgets.util,"escapeString"],[gadgets.util,"getFeatureParameters"],[gadgets.util,"getUrlParameters"],[gadgets.util,"hasFeature"],[gadgets.util,"registerOnLoadHandler"],[gadgets.util,"unescapeString"]])
});;
gadgets.rpctx=gadgets.rpctx||{};
if(!gadgets.rpctx.wpm){gadgets.rpctx.wpm=function(){var C,F;
var G;
var I=false;
var H=false;
var D=false;
function A(){var J=false;
function K(L){if(L.data=="postmessage.test"){J=true;
if(typeof L.origin==="undefined"){H=true
}}}gadgets.util.attachBrowserEvent(window,"message",K,false);
window.postMessage("postmessage.test","*");
if(J){I=true
}gadgets.util.removeBrowserEvent(window,"message",K,false)
}function E(L){var M=gadgets.json.parse(L.data);
if(D){if(!M||!M.f){return 
}var K=gadgets.rpc.getRelayUrl(M.f)||gadgets.util.getUrlParameters()["parent"];
var J=gadgets.rpc.getOrigin(K);
if(!H?L.origin!==J:L.domain!==/^.+:\/\/([^:]+).*/.exec(J)[1]){return 
}}C(M)
}function B(L,J){var K=gadgets.rpc._parseSiblingId(L);
if(K){return K.origin
}if(L==".."){return J
}else{return document.getElementById(L).src
}}return{getCode:function(){return"wpm"
},isParentVerifiable:function(){return true
},init:function(J,K){C=J;
F=K;
A();
if(!I){G=function(M,N,L){M.postMessage(N,L)
}
}else{G=function(M,N,L){window.setTimeout(function(){M.postMessage(N,L)
},0)
}
}gadgets.util.attachBrowserEvent(window,"message",E,false);
F("..",true);
return true
},setup:function(L,K,J){D=J;
if(L===".."){if(D){gadgets.rpc._createRelayIframe(K)
}else{gadgets.rpc.call(L,gadgets.rpc.ACK)
}}return true
},call:function(K,O,N){var M=gadgets.rpc._getTargetWin(K);
var L=gadgets.rpc.getRelayUrl(K)||B(K,gadgets.util.getUrlParameters()["parent"]);
var J=gadgets.rpc.getOrigin(L);
if(J){G(M,gadgets.json.stringify(N),J)
}else{gadgets.error("No relay set (used as window.postMessage targetOrigin), cannot send cross-domain message")
}return true
},relayOnload:function(K,J){F(K,true)
}}
}()
};;
;
gadgets.rpctx=gadgets.rpctx||{};
if(!gadgets.rpctx.frameElement){gadgets.rpctx.frameElement=function(){var E="__g2c_rpc";
var B="__c2g_rpc";
var D;
var C;
function A(G,K,J){try{if(K!==".."){var F=window.frameElement;
if(typeof F[E]==="function"){if(typeof F[E][B]!=="function"){F[E][B]=function(L){D(gadgets.json.parse(L))
}
}F[E](gadgets.json.stringify(J));
return true
}}else{var I=document.getElementById(G);
if(typeof I[E]==="function"&&typeof I[E][B]==="function"){I[E][B](gadgets.json.stringify(J));
return true
}}}catch(H){}return false
}return{getCode:function(){return"fe"
},isParentVerifiable:function(){return false
},init:function(F,G){D=F;
C=G;
return true
},setup:function(J,F){if(J!==".."){try{var I=document.getElementById(J);
I[E]=function(K){D(gadgets.json.parse(K))
}
}catch(H){return false
}}if(J===".."){C("..",true);
var G=function(){window.setTimeout(function(){gadgets.rpc.call(J,gadgets.rpc.ACK)
},500)
};
gadgets.util.registerOnLoadHandler(G)
}return true
},call:function(F,H,G){return A(F,H,G)
}}
}()
};;
;
gadgets.rpctx=gadgets.rpctx||{};
if(!gadgets.rpctx.nix){gadgets.rpctx.nix=function(){var C="GRPC____NIXVBS_wrapper";
var D="GRPC____NIXVBS_get_wrapper";
var F="GRPC____NIXVBS_handle_message";
var B="GRPC____NIXVBS_create_channel";
var A=10;
var J=500;
var I={};
var H;
var G=0;
function E(){var L=I[".."];
if(L){return 
}if(++G>A){gadgets.warn("Nix transport setup failed, falling back...");
H("..",false);
return 
}if(!L&&window.opener&&"GetAuthToken" in window.opener){L=window.opener;
if(L.GetAuthToken()==gadgets.rpc.getAuthToken("..")){var K=gadgets.rpc.getAuthToken("..");
L.CreateChannel(window[D]("..",K),K);
I[".."]=L;
window.opener=null;
H("..",true);
return 
}}window.setTimeout(function(){E()
},J)
}return{getCode:function(){return"nix"
},isParentVerifiable:function(){return false
},init:function(L,M){H=M;
if(typeof window[D]!=="unknown"){window[F]=function(O){window.setTimeout(function(){L(gadgets.json.parse(O))
},0)
};
window[B]=function(O,Q,P){if(gadgets.rpc.getAuthToken(O)===P){I[O]=Q;
H(O,true)
}};
var K="Class "+C+"\n Private m_Intended\nPrivate m_Auth\nPublic Sub SetIntendedName(name)\n If isEmpty(m_Intended) Then\nm_Intended = name\nEnd If\nEnd Sub\nPublic Sub SetAuth(auth)\n If isEmpty(m_Auth) Then\nm_Auth = auth\nEnd If\nEnd Sub\nPublic Sub SendMessage(data)\n "+F+"(data)\nEnd Sub\nPublic Function GetAuthToken()\n GetAuthToken = m_Auth\nEnd Function\nPublic Sub CreateChannel(channel, auth)\n Call "+B+"(m_Intended, channel, auth)\nEnd Sub\nEnd Class\nFunction "+D+"(name, auth)\nDim wrap\nSet wrap = New "+C+"\nwrap.SetIntendedName name\nwrap.SetAuth auth\nSet "+D+" = wrap\nEnd Function";
try{window.execScript(K,"vbscript")
}catch(N){return false
}}return true
},setup:function(O,K){if(O===".."){E();
return true
}try{var M=document.getElementById(O);
var N=window[D](O,K);
M.contentWindow.opener=N
}catch(L){return false
}return true
},call:function(K,N,M){try{if(I[K]){I[K].SendMessage(gadgets.json.stringify(M))
}}catch(L){return false
}return true
}}
}()
};;
;
gadgets.rpctx=gadgets.rpctx||{};
if(!gadgets.rpctx.rmr){gadgets.rpctx.rmr=function(){var G=500;
var E=10;
var H={};
var B;
var I;
function K(P,N,O,M){var Q=function(){document.body.appendChild(P);
P.src="about:blank";
if(M){P.onload=function(){L(M)
}
}P.src=N+"#"+O
};
if(document.body){Q()
}else{gadgets.util.registerOnLoadHandler(function(){Q()
})
}}function C(O){if(typeof H[O]==="object"){return 
}var P=document.createElement("iframe");
var M=P.style;
M.position="absolute";
M.top="0px";
M.border="0";
M.opacity="0";
M.width="10px";
M.height="1px";
P.id="rmrtransport-"+O;
P.name=P.id;
var N=gadgets.rpc.getRelayUrl(O);
if(!N){N=gadgets.rpc.getOrigin(gadgets.util.getUrlParameters()["parent"])+"/robots.txt"
}H[O]={frame:P,receiveWindow:null,relayUri:N,searchCounter:0,width:10,waiting:true,queue:[],sendId:0,recvId:0};
if(O!==".."){K(P,N,A(O))
}D(O)
}function D(O){var Q=null;
H[O].searchCounter++;
try{var N=gadgets.rpc._getTargetWin(O);
if(O===".."){Q=N.frames["rmrtransport-"+gadgets.rpc.RPC_ID]
}else{Q=N.frames["rmrtransport-.."]
}}catch(P){}var M=false;
if(Q){M=F(O,Q)
}if(!M){if(H[O].searchCounter>E){return 
}window.setTimeout(function(){D(O)
},G)
}}function J(N,P,T,S){var O=null;
if(T!==".."){O=H[".."]
}else{O=H[N]
}if(O){if(P!==gadgets.rpc.ACK){O.queue.push(S)
}if(O.waiting||(O.queue.length===0&&!(P===gadgets.rpc.ACK&&S&&S.ackAlone===true))){return true
}if(O.queue.length>0){O.waiting=true
}var M=O.relayUri+"#"+A(N);
try{O.frame.contentWindow.location=M;
var Q=O.width==10?20:10;
O.frame.style.width=Q+"px";
O.width=Q
}catch(R){return false
}}return true
}function A(N){var O=H[N];
var M={id:O.sendId};
if(O){M.d=Array.prototype.slice.call(O.queue,0);
M.d.push({s:gadgets.rpc.ACK,id:O.recvId})
}return gadgets.json.stringify(M)
}function L(X){var U=H[X];
var Q=U.receiveWindow.location.hash.substring(1);
var Y=gadgets.json.parse(decodeURIComponent(Q))||{};
var N=Y.d||[];
var O=false;
var T=false;
var V=0;
var M=(U.recvId-Y.id);
for(var P=0;
P<N.length;
++P){var S=N[P];
if(S.s===gadgets.rpc.ACK){I(X,true);
if(U.waiting){T=true
}U.waiting=false;
var R=Math.max(0,S.id-U.sendId);
U.queue.splice(0,R);
U.sendId=Math.max(U.sendId,S.id||0);
continue
}O=true;
if(++V<=M){continue
}++U.recvId;
B(S)
}if(O||(T&&U.queue.length>0)){var W=(X==="..")?gadgets.rpc.RPC_ID:"..";
J(X,gadgets.rpc.ACK,W,{ackAlone:O})
}}function F(P,S){var O=H[P];
try{var N=false;
N="document" in S;
if(!N){return false
}N=typeof S.document=="object";
if(!N){return false
}var R=S.location.href;
if(R==="about:blank"){return false
}}catch(M){return false
}O.receiveWindow=S;
function Q(){L(P)
}if(typeof S.attachEvent==="undefined"){S.onresize=Q
}else{S.attachEvent("onresize",Q)
}if(P===".."){K(O.frame,O.relayUri,A(P),P)
}else{L(P)
}return true
}return{getCode:function(){return"rmr"
},isParentVerifiable:function(){return true
},init:function(M,N){B=M;
I=N;
return true
},setup:function(O,M){try{C(O)
}catch(N){gadgets.warn("Caught exception setting up RMR: "+N);
return false
}return true
},call:function(M,O,N){return J(M,N.s,O,N)
}}
}()
};;
;
gadgets.rpctx=gadgets.rpctx||{};
if(!gadgets.rpctx.ifpc){gadgets.rpctx.ifpc=function(){var E=[];
var D=0;
var C;
function B(H){var F=[];
for(var I=0,G=H.length;
I<G;
++I){F.push(encodeURIComponent(gadgets.json.stringify(H[I])))
}return F.join("&")
}function A(I){var G;
for(var F=E.length-1;
F>=0;
--F){var J=E[F];
try{if(J&&(J.recyclable||J.readyState==="complete")){J.parentNode.removeChild(J);
if(window.ActiveXObject){E[F]=J=null;
E.splice(F,1)
}else{J.recyclable=false;
G=J;
break
}}}catch(H){}}if(!G){G=document.createElement("iframe");
G.style.border=G.style.width=G.style.height="0px";
G.style.visibility="hidden";
G.style.position="absolute";
G.onload=function(){this.recyclable=true
};
E.push(G)
}G.src=I;
window.setTimeout(function(){document.body.appendChild(G)
},0)
}return{getCode:function(){return"ifpc"
},isParentVerifiable:function(){return true
},init:function(F,G){C=G;
C("..",true);
return true
},setup:function(G,F){C(G,true);
return true
},call:function(F,K,I){var J=gadgets.rpc.getRelayUrl(F);
++D;
if(!J){gadgets.warn("No relay file assigned for IFPC");
return false
}var H=null;
if(I.l){var G=I.a;
H=[J,"#",B([K,D,1,0,B([K,I.s,"","",K].concat(G))])].join("")
}else{H=[J,"#",F,"&",K,"@",D,"&1&0&",encodeURIComponent(gadgets.json.stringify(I))].join("")
}A(H);
return true
}}
}()
};;
if(!gadgets.rpc){gadgets.rpc=function(){var d="__cb";
var j="";
var k="__ack";
var F=500;
var Y=10;
var C="|";
var N={};
var m={};
var U={};
var T={};
var R=0;
var J={};
var K={};
var h={};
var E={};
var L={};
var V={};
var S=(window.top!==window.self);
var P=window.name;
var b=function(){};
var g=0;
var p=1;
var A=2;
var i=(function(){function t(u){return function(){gadgets.log("gadgets.rpc."+u+"("+gadgets.json.stringify(Array.prototype.slice.call(arguments))+"): call ignored. [caller: "+document.location+", isChild: "+S+"]")
}
}return{getCode:function(){return"noop"
},isParentVerifiable:function(){return true
},init:t("init"),setup:t("setup"),call:t("call")}
})();
if(gadgets.util){E=gadgets.util.getUrlParameters()
}function c(){return typeof window.postMessage==="function"?gadgets.rpctx.wpm:typeof window.postMessage==="object"?gadgets.rpctx.wpm:window.ActiveXObject?gadgets.rpctx.nix:navigator.userAgent.indexOf("WebKit")>0?gadgets.rpctx.rmr:navigator.product==="Gecko"?gadgets.rpctx.frameElement:gadgets.rpctx.ifpc
}function I(y,w){var u=Z;
if(!w){u=i
}L[y]=u;
var t=V[y]||[];
for(var v=0;
v<t.length;
++v){var x=t[v];
x.t=X(y);
u.call(y,x.f,x)
}V[y]=[]
}var a=false,l=false;
function f(){if(l){return 
}function t(){a=true
}gadgets.util.attachBrowserEvent(window,"unload",t,false);
l=true
}function H(t,x,u,w,v){if(!T[x]||T[x]!==u){gadgets.error("Invalid auth token. "+T[x]+" vs "+u);
b(x,A)
}v.onunload=function(){if(K[x]&&!a){b(x,p);
gadgets.rpc.removeReceiver(x)
}};
f();
w=gadgets.json.parse(decodeURIComponent(w));
Z.relayOnload(x,w)
}function q(u){if(u&&typeof u.s==="string"&&typeof u.f==="string"&&u.a instanceof Array){if(T[u.f]){if(T[u.f]!==u.t){gadgets.error("Invalid auth token. "+T[u.f]+" vs "+u.t);
b(u.f,A)
}}if(u.s===k){window.setTimeout(function(){I(u.f,true)
},0);
return 
}if(u.c){u.callback=function(v){gadgets.rpc.call(u.f,d,null,u.c,v)
}
}var t=(N[u.s]||N[j]).apply(u,u.a);
if(u.c&&typeof t!=="undefined"){gadgets.rpc.call(u.f,d,null,u.c,t)
}}}function O(v){if(!v){return""
}v=v.toLowerCase();
if(v.indexOf("//")==0){v=window.location.protocol+v
}if(v.indexOf("://")==-1){v=window.location.protocol+"//"+v
}var w=v.substring(v.indexOf("://")+3);
var t=w.indexOf("/");
if(t!=-1){w=w.substring(0,t)
}var y=v.substring(0,v.indexOf("://"));
var x="";
var z=w.indexOf(":");
if(z!=-1){var u=w.substring(z+1);
w=w.substring(0,z);
if((y==="http"&&u!=="80")||(y==="https"&&u!=="443")){x=":"+u
}}return y+"://"+w+x
}function W(u,t){return"/"+u+(t?C+t:"")
}function Q(w){if(w[0]=="/"){var u=w.indexOf(C);
var v=u>0?w.substring(1,u):w.substring(1);
var t=u>0?w.substring(u+1):null;
return{id:v,origin:t}
}else{return null
}}function s(v){if(typeof v==="undefined"||v===".."){return window.parent
}var u=Q(v);
if(u){return window.top.frames[u.id]
}v=String(v);
var t=window.frames[v];
if(t){return t
}t=document.getElementById(v);
if(t&&t.contentWindow){return t.contentWindow
}return null
}var Z=c();
N[j]=function(){gadgets.warn("Unknown RPC service: "+this.s)
};
N[d]=function(u,t){var v=J[u];
if(v){delete J[u];
v(t)
}};
function o(w,u,t){if(K[w]===true){return 
}if(typeof K[w]==="undefined"){K[w]=0
}var v=s(w);
if(w===".."||v!=null){if(Z.setup(w,u,t)===true){K[w]=true;
return 
}}if(K[w]!==true&&K[w]++<Y){window.setTimeout(function(){o(w,u,t)
},F)
}else{L[w]=i;
K[w]=true
}}function e(u,x){if(typeof h[u]==="undefined"){h[u]=false;
var w=gadgets.rpc.getRelayUrl(u);
if(O(w)!==O(window.location.href)){return false
}var v=s(u);
try{h[u]=v.gadgets.rpc.receiveSameDomain
}catch(t){gadgets.error("Same domain call failed: parent= incorrectly set.")
}}if(typeof h[u]==="function"){h[u](x);
return true
}return false
}function r(u,t,v){if(!/http(s)?:\/\/.+/.test(t)){if(t.indexOf("//")==0){t=window.location.protocol+t
}else{if(t.charAt(0)=="/"){t=window.location.protocol+"//"+window.location.host+t
}else{if(t.indexOf("://")==-1){t=window.location.protocol+"//"+t
}}}}m[u]=t;
U[u]=!!v
}function X(t){return T[t]
}function D(t,v,u){v=v||"";
T[t]=String(v);
o(t,v,u)
}function B(t,v){function w(AA){var AC=AA?AA.rpc:{};
var y=AC.parentRelayUrl;
if(y.substring(0,7)!=="http://"&&y.substring(0,8)!=="https://"&&y.substring(0,2)!=="//"){if(typeof E.parent==="string"&&E.parent!==""){if(y.substring(0,1)!=="/"){var x=E.parent.lastIndexOf("/");
y=E.parent.substring(0,x+1)+y
}else{y=O(E.parent)+y
}}}var AB=!!AC.useLegacyProtocol;
r("..",y,AB);
if(AB){Z=gadgets.rpctx.ifpc;
Z.init(q,I)
}var z=v||E.forcesecure||false;
D("..",t,z)
}var u={parentRelayUrl:gadgets.config.NonEmptyStringValidator};
gadgets.config.register("rpc",u,w)
}function n(w,t,x){var u=x||E.forcesecure||false;
var v=t||E.parent;
if(v){r("..",v);
D("..",w,u)
}}function M(v,x,t,w){if(v[0]!="/"){if(!gadgets.util){return 
}var AB=document.getElementById(v);
if(!AB){throw new Error("Cannot set up gadgets.rpc receiver with ID: "+v+", element not found.")
}}var z=x||AB.src;
r(v,z);
var AA=gadgets.util.getUrlParameters(z);
var u=t||AA.rpctoken;
var y=w||AA.forcesecure;
D(v,u,y)
}function G(t,v,x,w){if(t===".."){var u=x||E.rpctoken||E.ifpctok||"";
if(window.__isgadget===true){B(u,w)
}else{n(u,v,w)
}}else{M(t,v,x,w)
}}return{config:function(t){if(typeof t.securityCallback==="function"){b=t.securityCallback
}},register:function(u,t){if(u===d||u===k){throw new Error("Cannot overwrite callback/ack service")
}if(u===j){throw new Error("Cannot overwrite default service: use registerDefault")
}N[u]=t
},unregister:function(t){if(t===d||t===k){throw new Error("Cannot delete callback/ack service")
}if(t===j){throw new Error("Cannot delete default service: use unregisterDefault")
}delete N[t]
},registerDefault:function(t){N[j]=t
},unregisterDefault:function(){delete N[j]
},forceParentVerifiable:function(){if(!Z.isParentVerifiable()){Z=gadgets.rpctx.ifpc
}},call:function(t,u,z,x){t=t||"..";
var y="..";
if(t===".."){y=P
}else{if(t[0]=="/"){y=W(P,gadgets.rpc.getOrigin(location.href))
}}++R;
if(z){J[R]=z
}var w={s:u,f:y,c:z?R:0,a:Array.prototype.slice.call(arguments,3),t:T[t],l:U[t]};
if(t!==".."&&!document.getElementById(t)){gadgets.log("WARNING: attempted send to nonexistent frame: "+t);
return 
}if(e(t,w)){return 
}var v=L[t];
if(!v){if(!V[t]){V[t]=[w]
}else{V[t].push(w)
}return 
}if(U[t]){v=gadgets.rpctx.ifpc
}if(v.call(t,y,w)===false){L[t]=i;
Z.call(t,y,w)
}},getRelayUrl:function(u){var t=m[u];
if(t&&t.substring(0,1)==="/"){if(t.substring(1,2)==="/"){t=document.location.protocol+t
}else{t=document.location.protocol+"//"+document.location.host+t
}}return t
},setRelayUrl:r,setAuthToken:D,setupReceiver:G,getAuthToken:X,removeReceiver:function(t){delete m[t];
delete U[t];
delete T[t];
delete K[t];
delete h[t];
delete L[t]
},getRelayChannel:function(){return Z.getCode()
},receive:function(u,t){if(u.length>4){q(gadgets.json.parse(decodeURIComponent(u[u.length-1])))
}else{H.apply(null,u.concat(t))
}},receiveSameDomain:function(t){t.a=Array.prototype.slice.call(t.a);
window.setTimeout(function(){q(t)
},0)
},getOrigin:O,getReceiverOrigin:function(v){var u=L[v];
if(!u){return null
}if(!u.isParentVerifiable(v)){return null
}var t=gadgets.rpc.getRelayUrl(v)||gadgets.util.getUrlParameters().parent;
return gadgets.rpc.getOrigin(t)
},init:function(){if(Z.init(q,I)===false){Z=i
}if(S){G("..")
}},_getTargetWin:s,_parseSiblingId:Q,_createRelayIframe:function(t,v){var y=gadgets.rpc.getRelayUrl("..");
if(!y){return null
}var x=y+"#..&"+P+"&"+t+"&"+encodeURIComponent(gadgets.json.stringify(v));
var u=document.createElement("iframe");
u.style.border=u.style.width=u.style.height="0px";
u.style.visibility="hidden";
u.style.position="absolute";
function w(){document.body.appendChild(u);
u.src='javascript:"<html></html>"';
u.src=x
}if(document.body){w()
}else{gadgets.util.registerOnLoadHandler(function(){w()
})
}return u
},ACK:k,RPC_ID:P,SEC_ERROR_LOAD_TIMEOUT:g,SEC_ERROR_FRAME_PHISH:p,SEC_ERROR_FORGED_MSG:A}
}();
gadgets.rpc.init()
};;
gadgets.config.init({"rpc":{"parentRelayUrl":"/rpc_relay.html"}});
var friendconnect_serverBase = "http://www.google.com";var friendconnect_loginUrl = "https://www.google.com/accounts";var friendconnect_gadgetPrefix = "http://www-a-fc-opensocial.googleusercontent.com/gadgets";
var friendconnect_serverVersion = "0.554.6";
var friendconnect_imageUrl = "http://www.google.com/friendconnect/scs/images";
var friendconnect_lightbox = true;
function fca(a){throw a;}var fcb=true,fcc=null,fcd=false,fce=gadgets,fcf=Error,fcg=undefined,fch=friendconnect_serverBase,fci=encodeURIComponent,fcaa=parseInt,fcj=String,fck=window,fcba=setTimeout,fcca=Object,fcl=document,fcm=Array,fcn=Math;function fcda(a,b){return a.toString=b}function fcea(a,b){return a.length=b}function fcfa(a,b){return a.className=b}function fco(a,b){return a.width=b}function fcp(a,b){return a.innerHTML=b}function fcq(a,b){return a.height=b}
var fcr="appendChild",fcs="push",fcga="toString",fct="length",fcha="propertyIsEnumerable",fcia="stringify",fc="prototype",fcja="test",fcu="width",fcv="round",fcw="slice",fcx="replace",fcy="document",fcka="data",fcz="split",fcA="getElementById",fcla="offsetWidth",fcB="charAt",fcC="location",fcD="getUrlParameters",fcE="indexOf",fcma="Dialog",fcF="style",fcna="body",fcG="left",fcH="call",fcI="match",fcJ="options",fcoa="random",fcK="createElement",fcpa="json",fcqa="addEventListener",fcra="bottom",fcL=
"setAttribute",fcsa="href",fcM="util",fcta="maxHeight",fcua="type",fcN="apply",fcva="auth",fcwa="reset",fcxa="getSecurityToken",fcya="bind",fcO="name",fcP="parentNode",fcza="display",fcQ="height",fcAa="offsetHeight",fcR="register",fcS="join",fcBa="unshift",fcCa="toLowerCase",fcT="right",goog=goog||{},fcU=this,fcEa=function(a,b,c){a=a[fcz](".");c=c||fcU;!(a[0]in c)&&c.execScript&&c.execScript("var "+a[0]);for(var d;a[fct]&&(d=a.shift());)if(!a[fct]&&fcDa(b))c[d]=b;else c=c[d]?c[d]:c[d]={}},fcFa=function(a){var b=
typeof a;if(b=="object")if(a){if(a instanceof fcm||!(a instanceof fcca)&&fcca[fc][fcga][fcH](a)=="[object Array]"||typeof a[fct]=="number"&&typeof a.splice!="undefined"&&typeof a[fcha]!="undefined"&&!a[fcha]("splice"))return"array";if(!(a instanceof fcca)&&(fcca[fc][fcga][fcH](a)=="[object Function]"||typeof a[fcH]!="undefined"&&typeof a[fcha]!="undefined"&&!a[fcha]("call")))return"function"}else return"null";else if(b=="function"&&typeof a[fcH]=="undefined")return"object";return b},fcDa=function(a){return a!==
fcg},fcGa=function(a){var b=fcFa(a);return b=="array"||b=="object"&&typeof a[fct]=="number"},fcV=function(a){return typeof a=="string"},fcHa=function(a){a=fcFa(a);return a=="object"||a=="array"||a=="function"};"closure_uid_"+fcn.floor(fcn[fcoa]()*2147483648)[fcga](36);
var fcIa=function(a){var b=fcFa(a);if(b=="object"||b=="array"){if(a.clone)return a.clone();b=b=="array"?[]:{};for(var c in a)b[c]=fcIa(a[c]);return b}return a},fcJa=function(a){return a[fcH][fcN](a[fcya],arguments)},fcKa=function(a,b){var c=b||fcU;if(arguments[fct]>2){var d=fcm[fc][fcw][fcH](arguments,2);return function(){var e=fcm[fc][fcw][fcH](arguments);fcm[fc][fcBa][fcN](e,d);return a[fcN](c,e)}}else return function(){return a[fcN](c,arguments)}},fcW=function(){fcW=Function[fc][fcya]&&Function[fc][fcya][fcga]()[fcE]("native code")!=
-1?fcJa:fcKa;return fcW[fcN](fcc,arguments)},fcLa=function(a){var b=fcm[fc][fcw][fcH](arguments,1);return function(){var c=fcm[fc][fcw][fcH](arguments);c[fcBa][fcN](c,b);return a[fcN](this,c)}},fcMa=function(a,b){for(var c in b)a[c]=b[c]},fcNa=Date.now||function(){return+new Date},fcX=function(a,b,c){fcEa(a,b,c)},fcY=function(a,b){function c(){}c.prototype=b[fc];a.Kc=b[fc];a.prototype=new c;a[fc].constructor=a};
Function[fc].bind=Function[fc][fcya]||function(a){if(arguments[fct]>1){var b=fcm[fc][fcw][fcH](arguments,1);b[fcBa](this,a);return fcW[fcN](fcc,b)}else return fcW(this,a)};SHA1=function(){this.c=fcm(5);this.ba=fcm(64);this.Bc=fcm(80);this.qa=fcm(64);this.qa[0]=128;for(var a=1;a<64;++a)this.qa[a]=0;this[fcwa]()};SHA1[fc].reset=function(){this.c[0]=1732584193;this.c[1]=4023233417;this.c[2]=2562383102;this.c[3]=271733878;this.c[4]=3285377520;this.wa=this.z=0};SHA1[fc].va=function(a,b){return(a<<b|a>>>32-b)&4294967295};
SHA1[fc].L=function(a){for(var b=this.Bc,c=0;c<64;c+=4){var d=a[c]<<24|a[c+1]<<16|a[c+2]<<8|a[c+3];b[c/4]=d}for(c=16;c<80;++c)b[c]=this.va(b[c-3]^b[c-8]^b[c-14]^b[c-16],1);a=this.c[0];d=this.c[1];var e=this.c[2],g=this.c[3],i=this.c[4],j,k;for(c=0;c<80;++c){if(c<40)if(c<20){j=g^d&(e^g);k=1518500249}else{j=d^e^g;k=1859775393}else if(c<60){j=d&e|g&(d|e);k=2400959708}else{j=d^e^g;k=3395469782}j=this.va(a,5)+j+i+k+b[c]&4294967295;i=g;g=e;e=this.va(d,30);d=a;a=j}this.c[0]=this.c[0]+a&4294967295;this.c[1]=
this.c[1]+d&4294967295;this.c[2]=this.c[2]+e&4294967295;this.c[3]=this.c[3]+g&4294967295;this.c[4]=this.c[4]+i&4294967295};SHA1[fc].update=function(a,b){if(!b)b=a[fct];var c=0;if(this.z==0)for(;c+64<b;){this.L(a[fcw](c,c+64));c+=64;this.wa+=64}for(;c<b;){this.ba[this.z++]=a[c++];++this.wa;if(this.z==64){this.z=0;for(this.L(this.ba);c+64<b;){this.L(a[fcw](c,c+64));c+=64;this.wa+=64}}}};
SHA1[fc].digest=function(){var a=fcm(20),b=this.wa*8;this.z<56?this.update(this.qa,56-this.z):this.update(this.qa,64-(this.z-56));for(var c=63;c>=56;--c){this.ba[c]=b&255;b>>>=8}this.L(this.ba);for(c=b=0;c<5;++c)for(var d=24;d>=0;d-=8)a[b++]=this.c[c]>>d&255;return a};G_HMAC=function(a,b,c){if(!a||typeof a!="object"||!a[fcwa]||!a.update||!a.digest)fca(fcf("Invalid hasher object. Hasher unspecified or does not implement expected interface."));if(b.constructor!=fcm)fca(fcf("Invalid key."));if(c&&typeof c!="number")fca(fcf("Invalid block size."));this.k=a;this.aa=c||16;this.Rb=fcm(this.aa);this.Qb=fcm(this.aa);if(b[fct]>this.aa){this.k.update(b);b=this.k.digest()}for(c=0;c<this.aa;c++){a=c<b[fct]?b[c]:0;this.Rb[c]=a^G_HMAC.OPAD;this.Qb[c]=a^G_HMAC.IPAD}};
G_HMAC.OPAD=92;G_HMAC.IPAD=54;G_HMAC[fc].reset=function(){this.k[fcwa]();this.k.update(this.Qb)};G_HMAC[fc].update=function(a){if(a.constructor!=fcm)fca(fcf("Invalid data. Data must be an array."));this.k.update(a)};G_HMAC[fc].digest=function(){var a=this.k.digest();this.k[fcwa]();this.k.update(this.Rb);this.k.update(a);return this.k.digest()};G_HMAC[fc].Fb=function(a){this[fcwa]();this.update(a);return this.digest()};var fcOa=function(a){for(var b=[],c=0,d=0;d<a[fct];d++){for(var e=a.charCodeAt(d);e>255;){b[c++]=e&255;e>>=8}b[c++]=e}return b};var fcPa=fcc,fcQa=fcc,fcRa=fcc,fcSa=fcc,fcUa=function(a,b){if(!fcGa(a))fca(fcf("encodeByteArray takes an array as a parameter"));fcTa();for(var c=b?fcRa:fcPa,d=[],e=0;e<a[fct];e+=3){var g=a[e],i=e+1<a[fct],j=i?a[e+1]:0,k=e+2<a[fct],l=k?a[e+2]:0,h=g>>2;g=(g&3)<<4|j>>4;j=(j&15)<<2|l>>6;l=l&63;if(!k){l=64;i||(j=64)}d[fcs](c[h],c[g],c[j],c[l])}return d[fcS]("")},fcVa=function(a,b){if(a[fct]%4)fca(fcf("Length of b64-encoded data must be zero mod four"));fcTa();for(var c=b?fcSa:fcQa,d=[],e=0;e<a[fct];e+=
4){var g=c[a[fcB](e)],i=c[a[fcB](e+1)],j=c[a[fcB](e+2)],k=c[a[fcB](e+3)];if(g==fcc||i==fcc||j==fcc||k==fcc)fca(fcf());g=g<<2|i>>4;d[fcs](g);if(j!=64){i=i<<4&240|j>>2;d[fcs](i);if(k!=64){j=j<<6&192|k;d[fcs](j)}}}return d},fcTa=function(){if(!fcPa){fcPa={};fcQa={};fcRa={};fcSa={};for(var a=0;a<65;a++){fcPa[a]="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="[fcB](a);fcQa[fcPa[a]]=a;fcRa[a]="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_."[fcB](a);fcSa[fcRa[a]]=a}}};var fcWa=function(a){for(var b=1;b<arguments[fct];b++){var c=fcj(arguments[b])[fcx](/\$/g,"$$$$");a=a[fcx](/\%s/,c)}return a},fcXa=function(a,b){var c=fcj(a)[fcCa](),d=fcj(b)[fcCa]();return c<d?-1:c==d?0:1},fc2a=function(a,b){if(b)return a[fcx](fcYa,"&amp;")[fcx](fcZa,"&lt;")[fcx](fc_a,"&gt;")[fcx](fc0a,"&quot;");else{if(!fc1a[fcja](a))return a;if(a[fcE]("&")!=-1)a=a[fcx](fcYa,"&amp;");if(a[fcE]("<")!=-1)a=a[fcx](fcZa,"&lt;");if(a[fcE](">")!=-1)a=a[fcx](fc_a,"&gt;");if(a[fcE]('"')!=-1)a=a[fcx](fc0a,
"&quot;");return a}},fcYa=/&/g,fcZa=/</g,fc_a=/>/g,fc0a=/\"/g,fc1a=/[&<>\"]/,fc4a=function(a,b){for(var c=0,d=fcj(a)[fcx](/^[\s\xa0]+|[\s\xa0]+$/g,"")[fcz]("."),e=fcj(b)[fcx](/^[\s\xa0]+|[\s\xa0]+$/g,"")[fcz]("."),g=fcn.max(d[fct],e[fct]),i=0;c==0&&i<g;i++){var j=d[i]||"",k=e[i]||"",l=RegExp("(\\d*)(\\D*)","g"),h=RegExp("(\\d*)(\\D*)","g");do{var f=l.exec(j)||["","",""],m=h.exec(k)||["","",""];if(f[0][fct]==0&&m[0][fct]==0)break;c=f[1][fct]==0?0:fcaa(f[1],10);var n=m[1][fct]==0?0:fcaa(m[1],10);c=
fc3a(c,n)||fc3a(f[2][fct]==0,m[2][fct]==0)||fc3a(f[2],m[2])}while(c==0)}return c},fc3a=function(a,b){if(a<b)return-1;else if(a>b)return 1;return 0};var fc5a,fc6a,fc7a,fc8a,fc9a,fc$a,fcab,fcbb,fccb,fcdb=function(){return fcU.navigator?fcU.navigator.userAgent:fcc},fceb=function(){return fcU.navigator},fcfb=function(){fc9a=fc8a=fc7a=fc6a=fc5a=fcd;var a;if(a=fcdb()){var b=fceb();fc5a=a[fcE]("Opera")==0;fc6a=!fc5a&&a[fcE]("MSIE")!=-1;fc8a=(fc7a=!fc5a&&a[fcE]("WebKit")!=-1)&&a[fcE]("Mobile")!=-1;fc9a=!fc5a&&!fc7a&&b.product=="Gecko"}};fcfb();
var fcgb=fc5a,fcZ=fc6a,fchb=fc9a,fcib=fc7a,fcjb=fc8a,fckb=function(){var a=fceb();return a&&a.platform||""},fclb=fckb(),fcmb=function(){fc$a=fclb[fcE]("Mac")!=-1;fcab=fclb[fcE]("Win")!=-1;fcbb=fclb[fcE]("Linux")!=-1;fccb=!!fceb()&&(fceb().appVersion||"")[fcE]("X11")!=-1};fcmb();
var fcob=function(){var a="",b;if(fcgb&&fcU.opera){a=fcU.opera.version;a=typeof a=="function"?a():a}else{if(fchb)b=/rv\:([^\);]+)(\)|;)/;else if(fcZ)b=/MSIE\s+([^\);]+)(\)|;)/;else if(fcib)b=/WebKit\/(\S+)/;if(b)a=(a=b.exec(fcdb()))?a[1]:""}if(fcZ){b=fcnb();if(b>parseFloat(a))return fcj(b)}return a},fcnb=function(){var a=fcU[fcy];return a?a.documentMode:fcg},fcpb=fcob(),fcqb={},fcrb=function(a){return fcqb[a]||(fcqb[a]=fc4a(fcpb,a)>=0)};var fcsb=/\s*;\s*/,fcub=function(a,b,c,d,e,g){if(/[;=\s]/[fcja](a))fca(fcf('Invalid cookie name "'+a+'"'));if(/[;\r\n]/[fcja](b))fca(fcf('Invalid cookie value "'+b+'"'));fcDa(c)||(c=-1);e=e?";domain="+e:"";d=d?";path="+d:"";g=g?";secure":"";if(c<0)c="";else if(c==0){c=new Date(1970,1,1);c=";expires="+c.toUTCString()}else{c=new Date(fcNa()+c*1E3);c=";expires="+c.toUTCString()}fctb(a+"="+b+e+d+c+g)},fcvb=function(a,b){for(var c=a+"=",d=(fcl.cookie||"")[fcz](fcsb),e=0,g;g=d[e];e++)if(g[fcE](c)==0)return g.substr(c[fct]);
return b},fcwb=function(a,b,c){var d=fcDa(fcvb(a));fcub(a,"",0,b,c);return d},fctb=function(a){fcl.cookie=a};var fcxb=function(a){this.stack=fcf().stack||"";if(a)this.message=fcj(a)};fcY(fcxb,fcf);fcxb[fc].name="CustomError";var fcyb=function(a,b){b[fcBa](a);fcxb[fcH](this,fcWa[fcN](fcc,b));b.shift();this.messagePattern=a};fcY(fcyb,fcxb);fcyb[fc].name="AssertionError";var fczb=function(a,b,c,d){var e="Assertion failed";if(c){e+=": "+c;var g=d}else if(a){e+=": "+a;g=b}fca(new fcyb(""+e,g||[]))},fcAb=function(a,b){a||fczb("",fcc,b,fcm[fc][fcw][fcH](arguments,2));return a};var fc_=fcm[fc],fcBb=fc_[fcE]?function(a,b,c){fcAb(a[fct]!=fcc);return fc_[fcE][fcH](a,b,c)}:function(a,b,c){c=c==fcc?0:c<0?fcn.max(0,a[fct]+c):c;if(fcV(a)){if(!fcV(b)||b[fct]!=1)return-1;return a[fcE](b,c)}for(c=c;c<a[fct];c++)if(c in a&&a[c]===b)return c;return-1},fcCb=fc_.forEach?function(a,b,c){fcAb(a[fct]!=fcc);fc_.forEach[fcH](a,b,c)}:function(a,b,c){for(var d=a[fct],e=fcV(a)?a[fcz](""):a,g=0;g<d;g++)g in e&&b[fcH](c,e[g],g,a)},fcDb=function(a,b){return fcBb(a,b)>=0},fcEb=function(){return fc_.concat[fcN](fc_,
arguments)},fcFb=function(a){if(fcFa(a)=="array")return fcEb(a);else{for(var b=[],c=0,d=a[fct];c<d;c++)b[c]=a[c];return b}},fcGb=function(a,b,c){fcAb(a[fct]!=fcc);return arguments[fct]<=2?fc_[fcw][fcH](a,b):fc_[fcw][fcH](a,b,c)};var fcHb=function(a,b){this.x=fcDa(a)?a:0;this.y=fcDa(b)?b:0};fcHb[fc].clone=function(){return new fcHb(this.x,this.y)};fcda(fcHb[fc],function(){return"("+this.x+", "+this.y+")"});var fc0=function(a,b){fco(this,a);fcq(this,b)};fc0[fc].clone=function(){return new fc0(this[fcu],this[fcQ])};fcda(fc0[fc],function(){return"("+this[fcu]+" x "+this[fcQ]+")"});fc0[fc].ceil=function(){fco(this,fcn.ceil(this[fcu]));fcq(this,fcn.ceil(this[fcQ]));return this};fc0[fc].floor=function(){fco(this,fcn.floor(this[fcu]));fcq(this,fcn.floor(this[fcQ]));return this};fc0[fc].round=function(){fco(this,fcn[fcv](this[fcu]));fcq(this,fcn[fcv](this[fcQ]));return this};
fc0[fc].scale=function(a){this.width*=a;this.height*=a;return this};var fcIb=function(a,b,c){for(var d in a)b[fcH](c,a[d],d,a)},fcJb=function(a){var b=[],c=0;for(var d in a)b[c++]=a[d];return b},fcKb=function(a){var b=[],c=0;for(var d in a)b[c++]=d;return b},fcLb=["constructor","hasOwnProperty","isPrototypeOf","propertyIsEnumerable","toLocaleString","toString","valueOf"],fcMb=function(a){for(var b,c,d=1;d<arguments[fct];d++){c=arguments[d];for(b in c)a[b]=c[b];for(var e=0;e<fcLb[fct];e++){b=fcLb[e];if(fcca[fc].hasOwnProperty[fcH](c,b))a[b]=c[b]}}};var fcNb=!fcZ||fcrb("9");fcZ&&!fcrb("9");var fcOb=function(a){return(a=a.className)&&typeof a[fcz]=="function"?a[fcz](/\s+/):[]},fcQb=function(a){var b=fcOb(a),c=fcGb(arguments,1);c=fcPb(b,c);fcfa(a,b[fcS](" "));return c},fcPb=function(a,b){for(var c=0,d=0;d<b[fct];d++)if(!fcDb(a,b[d])){a[fcs](b[d]);c++}return c==b[fct]};var fcRb=function(a){return fcV(a)?fcl[fcA](a):a},fcSb=fcRb,fcTb=function(a,b,c,d){a=d||a;b=b&&b!="*"?b.toUpperCase():"";if(a.querySelectorAll&&a.querySelector&&(!fcib||fcl.compatMode=="CSS1Compat"||fcrb("528"))&&(b||c)){c=b+(c?"."+c:"");return a.querySelectorAll(c)}if(c&&a.getElementsByClassName){a=a.getElementsByClassName(c);if(b){d={};for(var e=0,g=0,i;i=a[g];g++)if(b==i.nodeName)d[e++]=i;fcea(d,e);return d}else return a}a=a.getElementsByTagName(b||"*");if(c){d={};for(g=e=0;i=a[g];g++){b=i.className;
if(typeof b[fcz]=="function"&&fcDb(b[fcz](/\s+/),c))d[e++]=i}fcea(d,e);return d}else return a},fcVb=function(a,b){fcIb(b,function(c,d){if(d=="style")a[fcF].cssText=c;else if(d=="class")fcfa(a,c);else if(d=="for")a.htmlFor=c;else if(d in fcUb)a[fcL](fcUb[d],c);else a[d]=c})},fcUb={cellpadding:"cellPadding",cellspacing:"cellSpacing",colspan:"colSpan",rowspan:"rowSpan",valign:"vAlign",height:"height",width:"width",usemap:"useMap",frameborder:"frameBorder",type:"type"},fcWb=function(a){var b=a[fcy];if(fcib&&
!fcrb("500")&&!fcjb){if(typeof a.innerHeight=="undefined")a=fck;b=a.innerHeight;var c=a[fcy].documentElement.scrollHeight;if(a==a.top)if(c<b)b-=15;return new fc0(a.innerWidth,b)}a=b.compatMode=="CSS1Compat";if(fcgb&&!fcrb("9.50"))a=fcd;a=a?b.documentElement:b[fcna];return new fc0(a.clientWidth,a.clientHeight)},fc1=function(){return fcXb(fcl,arguments)},fcXb=function(a,b){var c=b[0],d=b[1];if(!fcNb&&d&&(d[fcO]||d[fcua])){c=["<",c];d[fcO]&&c[fcs](' name="',fc2a(d[fcO]),'"');if(d[fcua]){c[fcs](' type="',
fc2a(d[fcua]),'"');var e={};fcMb(e,d);d=e;delete d[fcua]}c[fcs](">");c=c[fcS]("")}c=a[fcK](c);if(d)if(fcV(d))fcfa(c,d);else fcFa(d)=="array"?fcQb[fcN](fcc,[c].concat(d)):fcVb(c,d);b[fct]>2&&fcYb(a,c,b,2);return c},fcYb=function(a,b,c,d){function e(i){if(i)b[fcr](fcV(i)?a.createTextNode(i):i)}for(d=d;d<c[fct];d++){var g=c[d];fcGa(g)&&!(fcHa(g)&&g.nodeType>0)?fcCb(fcZb(g)?fcFb(g):g,e):e(g)}},fc_b=function(a,b){a[fcr](b)},fc0b=function(a){return a&&a[fcP]?a[fcP].removeChild(a):fcc},fc1b=function(a,b){var c=
b[fcP];c&&c.replaceChild(a,b)},fc2b=function(a,b){if(a.contains&&b.nodeType==1)return a==b||a.contains(b);if(typeof a.compareDocumentPosition!="undefined")return a==b||Boolean(a.compareDocumentPosition(b)&16);for(;b&&a!=b;)b=b[fcP];return b==a},fcZb=function(a){if(a&&typeof a[fct]=="number")if(fcHa(a))return typeof a.item=="function"||typeof a.item=="string";else if(fcFa(a)=="function")return typeof a.item=="function";return fcd},fc3b=function(a){this.tb=a||fcU[fcy]||fcl};fc3b[fc].createElement=function(a){return this.tb[fcK](a)};
fc3b[fc].createTextNode=function(a){return this.tb.createTextNode(a)};fc3b[fc].appendChild=fc_b;fc3b[fc].removeNode=fc0b;fc3b[fc].replaceNode=fc1b;fc3b[fc].contains=fc2b;var fc4b="StopIteration"in fcU?fcU.StopIteration:fcf("StopIteration"),fc5b=function(){};fc5b[fc].next=function(){fca(fc4b)};fc5b[fc].__iterator__=function(){return this};var fc2=function(a){this.i={};this.e=[];var b=arguments[fct];if(b>1){if(b%2)fca(fcf("Uneven number of arguments"));for(var c=0;c<b;c+=2)this.set(arguments[c],arguments[c+1])}else a&&this.jb(a)};fc2[fc].q=0;fc2[fc].J=0;fc2[fc].Ka=function(){return this.q};fc2[fc].ja=function(){this.K();for(var a=[],b=0;b<this.e[fct];b++){var c=this.e[b];a[fcs](this.i[c])}return a};fc2[fc].P=function(){this.K();return this.e.concat()};fc2[fc].qb=function(a){return fc6b(this.i,a)};
fc2[fc].clear=function(){this.i={};fcea(this.e,0);this.J=this.q=0};fc2[fc].remove=function(a){if(fc6b(this.i,a)){delete this.i[a];this.q--;this.J++;this.e[fct]>2*this.q&&this.K();return fcb}return fcd};fc2[fc].K=function(){if(this.q!=this.e[fct]){for(var a=0,b=0;a<this.e[fct];){var c=this.e[a];if(fc6b(this.i,c))this.e[b++]=c;a++}fcea(this.e,b)}if(this.q!=this.e[fct]){var d={};for(b=a=0;a<this.e[fct];){c=this.e[a];if(!fc6b(d,c)){this.e[b++]=c;d[c]=1}a++}fcea(this.e,b)}};
fc2[fc].get=function(a,b){if(fc6b(this.i,a))return this.i[a];return b};fc2[fc].set=function(a,b){if(!fc6b(this.i,a)){this.q++;this.e[fcs](a);this.J++}this.i[a]=b};fc2[fc].jb=function(a){var b;if(a instanceof fc2){b=a.P();a=a.ja()}else{b=fcKb(a);a=fcJb(a)}for(var c=0;c<b[fct];c++)this.set(b[c],a[c])};fc2[fc].clone=function(){return new fc2(this)};
fc2[fc].__iterator__=function(a){this.K();var b=0,c=this.e,d=this.i,e=this.J,g=this,i=new fc5b;i.next=function(){for(;;){if(e!=g.J)fca(fcf("The map has changed since the iterator was created"));if(b>=c[fct])fca(fc4b);var j=c[b++];return a?j:d[j]}};return i};var fc6b=function(a,b){return fcca[fc].hasOwnProperty[fcH](a,b)};var fc7b=function(a,b,c,d){this.top=a;this.right=b;this.bottom=c;this.left=d};fc7b[fc].clone=function(){return new fc7b(this.top,this[fcT],this[fcra],this[fcG])};fcda(fc7b[fc],function(){return"("+this.top+"t, "+this[fcT]+"r, "+this[fcra]+"b, "+this[fcG]+"l)"});fc7b[fc].contains=function(a){return fc8b(this,a)};fc7b[fc].expand=function(a,b,c,d){if(fcHa(a)){this.top-=a.top;this.right+=a[fcT];this.bottom+=a[fcra];this.left-=a[fcG]}else{this.top-=a;this.right+=b;this.bottom+=c;this.left-=d}return this};
var fc8b=function(a,b){if(!a||!b)return fcd;if(b instanceof fc7b)return b[fcG]>=a[fcG]&&b[fcT]<=a[fcT]&&b.top>=a.top&&b[fcra]<=a[fcra];return b.x>=a[fcG]&&b.x<=a[fcT]&&b.y>=a.top&&b.y<=a[fcra]};var fc9b=function(a,b,c,d){this.left=a;this.top=b;fco(this,c);fcq(this,d)};fc9b[fc].clone=function(){return new fc9b(this[fcG],this.top,this[fcu],this[fcQ])};fcda(fc9b[fc],function(){return"("+this[fcG]+", "+this.top+" - "+this[fcu]+"w x "+this[fcQ]+"h)"});fc9b[fc].contains=function(a){return a instanceof fc9b?this[fcG]<=a[fcG]&&this[fcG]+this[fcu]>=a[fcG]+a[fcu]&&this.top<=a.top&&this.top+this[fcQ]>=a.top+a[fcQ]:a.x>=this[fcG]&&a.x<=this[fcG]+this[fcu]&&a.y>=this.top&&a.y<=this.top+this[fcQ]};var fcac=function(a,b,c){fcV(b)?fc$b(a,c,b):fcIb(b,fcLa(fc$b,a))},fc$b=function(a,b,c){a[fcF][fcbc(c)]=b},fccc=function(a,b){var c=a.nodeType==9?a:a.ownerDocument||a[fcy];if(c.defaultView&&c.defaultView.getComputedStyle)if(c=c.defaultView.getComputedStyle(a,fcc))return c[b]||c.getPropertyValue(b);return""},fcfc=function(a,b,c){if(b instanceof fc0){c=b[fcQ];b=b[fcu]}else{if(c==fcg)fca(fcf("missing height argument"));c=c}fcdc(a,b);fcec(a,c)},fcgc=function(a,b){if(typeof a=="number")a=(b?fcn[fcv](a):
a)+"px";return a},fcec=function(a,b){fcq(a[fcF],fcgc(b,fcb))},fcdc=function(a,b){fco(a[fcF],fcgc(b,fcb))},fchc=function(a){var b=fcgb&&!fcrb("10");if((fccc(a,"display")||(a.currentStyle?a.currentStyle[fcza]:fcc)||a[fcF][fcza])!="none")return b?new fc0(a[fcla]||a.clientWidth,a[fcAa]||a.clientHeight):new fc0(a[fcla],a[fcAa]);var c=a[fcF],d=c[fcza],e=c.visibility,g=c.position;c.visibility="hidden";c.position="absolute";c.display="inline";if(b){b=a[fcla]||a.clientWidth;a=a[fcAa]||a.clientHeight}else{b=
a[fcla];a=a[fcAa]}c.display=d;c.position=g;c.visibility=e;return new fc0(b,a)},fcic={},fcbc=function(a){return fcic[a]||(fcic[a]=fcj(a)[fcx](/\-([a-z])/g,function(b,c){return c.toUpperCase()}))},fcjc=function(a,b){a[fcF].display=b?"":"none"};var fckc={},fclc={};var fcmc=function(a,b,c,d){b=b||"800";c=c||"550";d=d||"friendconnect";a=fck.open(a,d,"menubar=no,toolbar=no,dialog=yes,location=yes,alwaysRaised=yes,width="+b+",height="+c+",resizable=yes,scrollbars=1,status=1");fck.focus&&a&&a.focus()},fcnc=function(a,b){var c=fce[fcM][fcD]().communityId;fce.rpc[fcH](fcc,"signin",fcc,c,a,b)};fcX("goog.peoplesense.util.openPopup",fcmc);fcX("goog.peoplesense.util.finishSignIn",fcnc);var fcqc=function(a,b){var c=fcoc()+"/friendconnect/invite/friends",d=fci(shindig[fcva][fcxa]());fcpc(c,d,a,b)},fcpc=function(a,b,c,d){a+="?st="+b;if(c)a+="&customMessage="+fci(c);if(d)a+="&customInviteUrl="+fci(d);b=760;if(fcZ)b+=25;fcmc(a,fcj(b),"515","friendconnect_invite")};fcX("goog.peoplesense.util.invite",fcqc);fcX("goog.peoplesense.util.inviteFriends",fcpc);var fcrc=function(a){this.url=a};fcrc[fc].l=function(a,b){if(this.url[fcE]("?"+a+"=")>=0||this.url[fcE]("&"+a+"=")>=0)fca(fcf("duplicate: "+a));if(b===fcc||b===fcg)return this;var c=this.url[fcE]("?")>=0?"&":"?";this.url+=c+a+"="+fci(b);return this};fcda(fcrc[fc],function(){return this.url});var fcoc=function(){return fck.friendconnect_serverBase},fcsc=function(a,b,c,d,e,g,i){b=b||"800";c=c||"550";d=d||"friendconnect";g=g||fcd;fce.rpc[fcH](fcc,"openLightboxIframe",i,a,shindig[fcva][fcxa](),b,c,d,e,fcc,fcc,fcc,g)},fctc=function(a,b){var c=fce[fcM][fcD]().psinvite||"",d=new fcrc(fcoc()+"/friendconnect/signin/home");d.l("st",fck.shindig[fcva][fcxa]());d.l("psinvite",c);d.l("iframeId",a);d.l("loginProvider",b);d.l("subscribeOnSignin","1");fcmc(d[fcga]());return fcd},fcuc=function(){var a=
fce[fcM][fcD]().communityId;fce.rpc[fcH](fcc,"signout",fcc,a)},fcwc=function(a,b){var c=fcoc()+"/friendconnect/settings/edit?st="+fci(shindig[fcva][fcxa]())+(a?"&iframeId="+fci(a):"");if(b)c=c+"&"+b;fcvc(c)},fcxc=function(a){a=fcoc()+"/friendconnect/settings/siteProfile?st="+fci(a);fcvc(a)},fcvc=function(a){var b=800,c=510;if(fcZ)b+=25;fcmc(a,fcj(b),fcj(c))},fcyc=function(a,b,c,d){d=d||2;var e=fcc;if(b=="text"){e=fc1("div",{"class":"gfc-button-text"},fc1("div",{"class":"gfc-icon"},fc1("a",{href:"javascript:void(0);"},
c)));a[fcr](e)}else if(b=="long"||b=="standard"){e=d==1?fc1("div",{"class":"gfc-inline-block gfc-primaryactionbutton gfc-button-base"},fc1("div",{"class":"gfc-inline-block gfc-button-base-outer-box"},fc1("div",{"class":"gfc-inline-block gfc-button-base-inner-box"},fc1("div",{"class":"gfc-button-base-pos"},fc1("div",{"class":"gfc-button-base-top-shadow",innerHTML:"&nbsp;"}),fc1("div",{"class":"gfc-button-base-content"},fc1("div",{"class":"gfc-icon"},c)))))):fc1("table",{"class":"gfc-button-base-v2 gfc-button",
cellpadding:"0",cellspacing:"0"},fc1("tbody",{"class":""},fc1("tr",{"class":""},fc1("td",{"class":"gfc-button-base-v2 gfc-button-1"}),fc1("td",{"class":"gfc-button-base-v2 gfc-button-2"},c),fc1("td",{"class":"gfc-button-base-v2 gfc-button-3"}))));a[fcr](e);if(b=="standard"){b=fc1("div",{"class":"gfc-footer-msg"},"with Google Friend Connect");d==1&&a[fcr](fc1("br"));a[fcr](b)}}return e},fczc=function(a,b){if(!a)fca("google.friendconnect.renderSignInButton: missing options");var c=a[fcF]||"standard",
d=a.text,e=a.version;if(c=="standard")d=a.text||"Sign in";else if(c=="text"||c=="long")d=a.text||"Sign in with Friend Connect";var g=a.element;if(!g){g=a.id;if(!g)fca("google.friendconnect.renderSignInButton: options[id] and options[element] == null");g=fcSb(g);if(!g)fca("google.friendconnect.renderSignInButton: element "+a.id+" not found")}fcp(g,"");c=fcyc(g,c,d,e);fck[fcqa]?c[fcqa]("click",b,fcd):c.attachEvent("onclick",b)},fcAc=function(a,b){b=b||fcW(fctc,fcc,fcc,fcc,fcc);fczc(a,b)},fcBc=function(a,
b){fce.rpc[fcH](fcc,"putReloadViewParam",fcc,a,b);var c=fce.views.getParams();c[a]=b},fcCc=function(a,b){var c=new fcrc("/friendconnect/gadgetshare/friends");c.l("customMessage",a);c.l("customInviteUrl",b);c.l("container","glb");var d=310;if(fcZ)d+=25;fcsc(c[fcga](),fcj(d),"370")};fcX("goog.peoplesense.util.getBaseUrl",fcoc);fcX("goog.peoplesense.util.finishSignIn",fcnc);fcX("goog.peoplesense.util.signout",fcuc);fcX("goog.peoplesense.util.signin",fctc);fcX("goog.peoplesense.util.editSettings",fcwc);
fcX("goog.peoplesense.util.editSSProfile",fcxc);fcX("goog.peoplesense.util.setStickyViewParamToken",fcBc);fcX("google.friendconnect.renderSignInButton",fcAc);fcX("goog.peoplesense.util.share",fcCc);fcX("goog.peoplesense.util.userAgent.IE",fcZ);var fcDc={},fcEc={},fc3=function(a){this.h=new fc2;this.snippetId=a.id;this.site=a.site;a=a["view-params"];var b=a.skin;this.bc=(b?b.POSITION:"top")||"top";this.Cc={allowAnonymousPost:a.allowAnonymousPost||fcd,scope:a.scope||"SITE",docId:a.docId||"",features:a.features||"video,comment",startMaximized:"true",disableMinMax:"true",skin:b};this.absoluteBottom=fcZ&&!fcrb("7");this.fixedIeSizes=fcZ;fck[fcqa]?fck[fcqa]("resize",fcW(this.$a,this),fcd):fck.attachEvent("onresize",fcW(this.$a,this));this.ob()};
fc3[fc].ob=function(){if(!this.site)fca(fcf("Must supply site ID."));if(!this.snippetId)fca(fcf("Must supply a snippet ID."))};fc3[fc].b=10;fc3[fc].za=1;fc3[fc].p="fc-friendbar-";fc3[fc].t=fc3[fc].p+"outer";fc3[fc].cb=fc3[fc].t+"-shadow";fc3[fc].render=function(){fcl.write(this.xb());var a=fcRb(this.snippetId);fcp(a,this.N())};fc3[fc].yb=function(){var a=fcRb(this.t);return a=fchc(a)[fcu]};fc3[fc].$a=function(){for(var a=this.h.P(),b=0;b<a[fct];b++)this.nc(a[b]);goog&&fckc&&fclc&&fcFc&&fcGc("resize")};
fc3[fc].n=function(){return this.bc};fc3[fc].d=function(a){return this.p+"shadow-"+a};fc3[fc].ha=function(a){return this.p+"menus-"+a};fc3[fc].Q=function(a){return this.p+a+"Target"};fc3[fc].fa=function(a){return this.p+a+"Drawer"};fc3[fc].Pa=function(){return this.Q("")};fc3[fc].Qa=function(){return this.p+"wallpaper"};fc3[fc].La=function(){return this.fa("")};
fc3[fc].xb=function(){var a=fck.friendconnect_imageUrl+"/",b=a+"shadow_tc.png",c=a+"shadow_bc.png",d=a+"shadow_bl.png",e=a+"shadow_tl.png",g=a+"shadow_tr.png",i=a+"shadow_br.png";a=a+"shadow_cr.png";var j=function(m,n){return fcZ?'filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(src="'+m+'", sizingMethod="scale");':"background-image: url("+m+");background-repeat: "+n+"; "},k="position:absolute; top:";if(this.n()!="top"){k="position:fixed; bottom:";if(this.absoluteBottom)k="position:absolute; bottom:"}var l=
c;if(this.n()!="top")l=b;var h=0,f=[];f[h++]='<style type="text/css">';if(this.n()!="top"&&this.absoluteBottom)f[h++]="html, body {height: 100%; overflow: auto; };";f[h++]="#"+this.t+" {";f[h++]="background:#E0ECFF;";f[h++]="left:0;";f[h++]="height: "+(fcZ?"35px;":"36px;");if(this.n()!="top"&&this.absoluteBottom)f[h++]="margin-right: 20px;";f[h++]="padding:0;";f[h++]=k+" 0;";f[h++]="width:100%;";f[h++]="z-index:5000;";f[h++]="}";f[h++]="#"+this.cb+" {";f[h++]=j(l,"repeat-x");f[h++]="left:0;";f[h++]=
"height:"+this.b+"px;";if(this.n()!="top"&&this.absoluteBottom)f[h++]="margin-right: 20px;";f[h++]="padding:0;";f[h++]=k+(fcZ?"35px;":"36px;");f[h++]="width:100%;";f[h++]="z-index:4998;";f[h++]="}";f[h++]="."+this.La()+" {";f[h++]="display: block;";f[h++]="padding:0;";f[h++]=k+(fcZ?"34px;":"35px;");f[h++]="z-index:4999;";f[h++]="}";f[h++]="."+this.Qa()+" {";f[h++]="background: white;";f[h++]="height: 100%;";f[h++]="margin-right: "+this.b+"px;";f[h++]="}";f[h++]="."+this.Pa()+" {";f[h++]="border: "+
this.za+"px solid #ccc;";f[h++]="height: 100%;";f[h++]="left: 0;";f[h++]="background-image: url("+fck.friendconnect_imageUrl+"/loading.gif);";f[h++]="background-position: center;";f[h++]="background-repeat: no-repeat;";f[h++]="}";f[h++]="."+this.d("cr")+" {";f[h++]=j(a,"repeat-y");f[h++]="height: 100%;";f[h++]="position:absolute;";f[h++]="right: 0;";f[h++]="top: 0;";f[h++]="width:"+this.b+"px;";f[h++]="}";f[h++]="."+this.d("bl")+" {";f[h++]=j(d,"no-repeat");f[h++]="height: "+this.b+"px;";f[h++]="position:absolute;";
f[h++]="width:"+this.b+"px;";f[h++]="}";f[h++]="."+this.d("tl")+" {";f[h++]=j(e,"no-repeat");f[h++]="height: "+this.b+"px;";f[h++]="position:absolute;";f[h++]="left:0px;";f[h++]="width:"+this.b+"px;";f[h++]="}";f[h++]="."+this.d("bc")+" {";f[h++]=j(c,"repeat-x");f[h++]="height: "+this.b+"px;";f[h++]="left: "+this.b+"px;";f[h++]="position:absolute;";f[h++]="right: "+this.b+"px;";f[h++]="}";f[h++]="."+this.d("tc")+" {";f[h++]=j(b,"repeat-x");f[h++]="height: "+this.b+"px;";f[h++]="left: "+this.b+"px;";
f[h++]="margin-left: "+this.b+"px;";f[h++]="margin-right: "+this.b+"px;";f[h++]="right: "+this.b+"px;";f[h++]="}";f[h++]="."+this.d("br")+" {";f[h++]=j(i,"no-repeat");f[h++]="height: "+this.b+"px;";f[h++]="position:absolute;";f[h++]="right: 0;";f[h++]="width: "+this.b+"px;";f[h++]="}";f[h++]="."+this.d("tr")+" {";f[h++]=j(g,"no-repeat");f[h++]="height: "+this.b+"px;";f[h++]="position:absolute;";f[h++]="right: 0;";f[h++]="top: 0;";f[h++]="width: "+this.b+"px;";f[h++]="}";f[h++]="</style>";return f[fcS]("")};
fc3[fc].N=function(){var a=['<div id="'+this.t+'"></div>','<div id="'+this.cb+'"></div>','<div id="'+this.ha(this.h.Ka())+'"></div>'];return a[fcS]("")};fc3[fc].rb=function(a,b,c,d){if(!this.h.qb(a)){b=new fc4(this,a,b,c,d);c=this.h.Ka();d=fcRb(this.ha(c));fcp(d,b.N()+'<div id="'+this.ha(c+1)+'"></div>');this.h.set(a,b)}};fc3[fc].la=function(a){(a=this.h.get(a))&&a.drawer&&fcjc(a.drawer,fcd)};fc3[fc].dc=function(a){if(a=this.h.get(a))a.rendered=fcd};
fc3[fc].refresh=function(){for(var a=this.h.P(),b=0;b<a[fct];b++){var c=a[b];this.la(c);this.dc(c)}};fc3[fc].Yb=function(a){for(var b=this.h.ja(),c=0;c<b[fct];c++){var d=b[c];if(d.id==a){d.zc();break}}};fc3[fc].Xb=function(a){for(var b=this.h.ja(),c=0;c<b[fct];c++){var d=b[c];if(d.id==a){d.Ub();break}}};fc3[fc].nc=function(a){if((a=this.h.get(a))&&a.drawer&&a.na()){a.da();a.Ha();a.ya()}};
fc3[fc].yc=function(a,b){var c=this.h.get(a);if(c){if(!c.drawer){c.drawer=fcRb(this.fa(c[fcO]));c.target=fcRb(this.Q(c[fcO]));c.sha_bc=fcTb(fcl,"div",this.n()=="top"?this.d("bc"):this.d("tc"),c.drawer)[0];c.sha_cr=fcTb(fcl,"div",this.d("cr"),c.drawer)[0]}for(var d=this.h.P(),e=0;e<d[fct];e++){var g=d[e];a!==g&&this.la(g)}c.da(b);fcjc(c.drawer,fcb);fck.setTimeout(function(){c.ya();c.Ha();c.render()},0)}};
var fc4=function(a,b,c,d,e){this.id=-1;this.bar=a;this.name=b;this.constraints=d;this.skin=e||{};fcq(this,this.skin.HEIGHT||"0");this.url=fck.friendconnect_serverBase+c;this.sha_bc=this.target=this.drawer=fcc;this.loaded=this.rendered=fcd;this.da()};
fc4[fc].da=function(a){fcMb(this.constraints,a||{});fcMb(this.skin,this.constraints);if(this.bar.fixedIeSizes&&this.constraints[fcG]&&this.constraints[fcT]){a=this.bar.yb();var b=this.constraints[fcG],c=this.constraints[fcT];a=a-(b+c);if(a%2){a-=1;this.skin.right+=1}fco(this.skin,a);delete this.skin[fcG]}};
fc4[fc].ya=function(){if(this.drawer){if(this.skin[fcu]){var a=this.bar.za,b=this.bar.b,c=fcZ?2:0;fcfc(this.target,this.skin[fcu],"");fcfc(this.sha_bc,this.skin[fcu]-b+2*a-c,"");this.skin.rightShadow?fcfc(this.drawer,this.skin[fcu]+b+2*a-c,""):fcfc(this.drawer,this.skin[fcu]+2*a-c,"")}if(this.skin[fcT])this.drawer[fcF].right=this.skin[fcT]+0+"px"}};
fc4[fc].Ha=function(){if(fcZ&&this.drawer){var a=fchc(this.target),b=a[fcu]-this.bar.b;a=a[fcQ];if(b<0)b=0;this.sha_bc&&this.sha_bc[fcF]&&fcfc(this.sha_bc,b,"");this.sha_cr&&this.sha_cr[fcF]&&fcfc(this.sha_cr,"",a)}};
fc4[fc].N=function(){var a="display:none;",b="position: relative; ",c="",d="",e="",g="",i=!!this.skin.rightShadow;if(!i){c+="display: none; ";e+="display: none; ";d+="right: 0px; ";g+="margin-right: 0px; "}for(var j in this.skin){var k=Number(this.skin[j]);if(i&&fcXa(j,"width")==0)k+=this.bar.b;if(fcXa(j,"height")==0)b+=j+": "+k+"px; ";if(j!="rightShadow"){if(fcXa(j,"height")==0)k+=this.bar.b;if(fcXa(j,"width")==0)k+=2;a+=j+": "+k+"px; "}if(fcZ&&fcXa(j,"width")==0){k-=i?2*this.bar.b:this.bar.b;d+=
j+": "+k+"px; "}}if(fcZ&&(this[fcQ]|0)>0){i=(this[fcQ]|0)+2;c+="height: "+i+"px; "}i=0;j=[];j[i++]='<div id="'+this.bar.fa(this[fcO])+'"class="'+this.bar.La()+'"style="'+a+'"> ';if(this.bar.n()=="bottom")j[i++]='<div class="'+this.bar.d("tl")+'"></div> <div class="'+this.bar.d("tc")+'"style="'+d+'"></div> <div class="'+this.bar.d("tr")+'"style="'+e+'"></div> ';j[i++]='<div style="'+b+'"> <div class="'+this.bar.Qa()+'"style="'+g+'"><div id="'+this.bar.Q(this[fcO])+'"class="'+this.bar.Pa()+'"></div> <div class="'+
this.bar.d("cr")+'"style="'+c+'"></div> </div> </div> ';if(this.bar.n()=="top")j[i++]='<div class="'+this.bar.d("bl")+'"></div> <div class="'+this.bar.d("bc")+'"style="'+d+'"></div> <div class="'+this.bar.d("br")+'"style="'+e+'"></div> ';j[i++]="</div> ";return j[fcS]("")};fc4[fc].zc=function(){this.rendered=this.na()};fc4[fc].Ub=function(){this.loaded=this.na()};fc4[fc].na=function(){return!!this.drawer&&this.drawer[fcF][fcza]!="none"};
fc4[fc].render=function(){if(this.rendered==fcd){var a={};a.url=this.url;a.id=this.bar.Q(this[fcO]);a.site=this.bar.site;a["view-params"]=fcIa(this.bar.Cc);if(this[fcO]=="profile")a["view-params"].profileId="VIEWER";this.skin&&fcMb(a["view-params"].skin,this.skin);a["view-params"].menuName=this[fcO];a["view-params"].opaque="true";a["view-params"].menuPosition=this.bar.bc;fcq(a,"1px");if(fcDc&&fcEc&&fc5)this.id=fc5.render(a)}};fcX("google.friendconnect.FriendBar",fc3);var fcHc="0123456789abcdefghijklmnopqrstuv",fcJc=function(a){a=new fcIc(a);if(a.pa()%5)fca(fcf());for(var b=[],c=0;a.pa()>0;c++)b[c]=fcHc[fcB](a.$b(5));return b[fcS]("")},fcIc=function(a){this.D=this.r=0;this.ca=a};fcIc[fc].pa=function(){return 8*(this.ca[fct]-this.D)-this.r};
fcIc[fc].$b=function(a){var b=0;if(a>this.pa())fca(fcf());if(this.r>0){b=255>>this.r&this.ca[this.D];var c=8-this.r,d=fcn.min(a%8,c);c=c-d;b=b>>c;a-=d;this.r+=d;if(this.r==8){this.r=0;this.D++}}for(;a>=8;){b<<=8;b|=this.ca[this.D];this.D++;a-=8}if(a>0){b<<=a;b|=this.ca[this.D]>>8-a;this.r=a}return b};var fcKc=function(){};fcKc[fc].F=function(){};fcKc[fc].I=function(){};var fcLc=(new Date).getTime(),fc6=function(){},fcMc=function(){},fcNc=function(){},fcOc=function(){};fcY(fcOc,fcNc);var fcPc=function(a){if(a)for(var b in a)if(a.hasOwnProperty(b))this[b]=a[b];if(this.viewParams)for(var c in this.viewParams)if(/^FC_RELOAD_.*$/[fcja](c))this.viewParams[c]=fcc};fcPc[fc].render=function(a){var b=this;if(a){b.Ac();this.Ab(function(c){fcac(a,"visibility","hidden");fcp(a,c);b.refresh(a,c);c=function(d){fcac(d,"visibility","visible")};c=fcLa(c,a);fcba(c,500);b.chrome=a})}};
fcPc[fc].Ab=function(a){return this.Gb(a)};var fc7=function(a){fcPc[fcH](this,a);this.U="../../";this.rpcToken=fcj(fcn[fcv](2147483647*fcn[fcoa]()))};fcY(fc7,fcPc);fc7[fc].hb="gfc_iframe_";fc7[fc].ib="friendconnect";fc7[fc].Ia="";fc7[fc].oc="rpc_relay.html";fc7[fc].X=function(a){this.U=a};fc7[fc].Ac=function(){return this.Ia=fcj(fcn[fcv](2147483647*fcn[fcoa]()))};fc7[fc].ga=function(){return this.hb+this.Ia+"_"+this.id};
fc7[fc].refresh=function(a,b){var c=fc5.Fc,d,e={},g=fc5.Ja(this.communityId),i=g[fcz]("~"),j=fc5.sb;if(j&&i[fct]>1){d=i[2];i=i[1];var k=[this.specUrl,this.communityId,i,j][fcS](":");e.sig=fc5.hash(d,k);e.userId=i;e.dateStamp=j}e.container=this.ib;e.mid=this.id;e.nocache=fc5.ac;e.view=this.Z;e.parent=fc5.S;if(this.debug)e.debug="1";if(this.specUrl)e.url=this.specUrl;if(this.communityId){j=fce[fcM][fcD]().profileId;e.communityId=this.communityId;if(d=fce[fcM][fcD]().psinvite)e.psinvite=d;if(j)e.profileId=
j}e.caller=fcQc();e.rpctoken=this.rpcToken;j=fcd;d="";i=/Version\/3\..*Safari/;if((i=fcib&&fcdb()[fcI](i))||!fc5.R[this.specUrl]&&this.viewParams){e["view-params"]=fce[fcpa][fcia](this.viewParams);d="?viewParams="+fci(e["view-params"]);j=fcb}if(this.prefs)e.prefs=fce[fcpa][fcia](this.prefs);if(this.viewParams&&this.sendViewParamsToServer)e["view-params"]=fce[fcpa][fcia](this.viewParams);if(this.locale)e.locale=this.locale;if(this.secureToken)e.st=this.secureToken;i=fc5.Oa(this.specUrl);d=i+"ifr"+
d+(this.hashData?"&"+this.hashData:"");if(fc5.Ec!=1||j||g||this.secureToken){if(g&&!e.sig)e.fcauth=g}else e.sig||(c="get");g=this.ga();fcRc(g,d,c,e,a,b,this.rpcToken)};var fc8=function(){this.j={};this.S="http://"+fcl[fcC].host;this.Z="default";this.ac=1;this.Jc=0;this.Gc="US";this.Hc="en";this.Ic=2147483647};fcY(fc8,fcMc);fc8[fc].v=fcPc;fc8[fc].A=new fcOc;fc8[fc].bb=function(a){this.ac=a};fc8[fc].Ga=function(a){this.Ec=a};fc8[fc].Na=function(a){return"gadget_"+a};fc8[fc].w=function(a){return this.j[this.Na(a)]};
fc8[fc].M=function(a){return new this.v(a)};fc8[fc].kb=function(a){a.id=this.Hb();this.j[this.Na(a.id)]=a};fc8[fc].Zb=0;fc8[fc].Hb=function(){return this.Zb++};var fcTc=function(){fc8[fcH](this);this.A=new fcSc};fcY(fcTc,fc8);fcTc[fc].v=fc7;fcTc[fc].W=function(a){a[fcI](/^http[s]?:\/\//)||(a=fcl[fcC][fcsa][fcI](/^[^?#]+\//)[0]+a);this.S=a};fcTc[fc].H=function(a){var b=this.A.Ma(a);a.render(b)};var fcSc=function(){this.wb={}};fcY(fcSc,fcNc);
fcSc[fc].lb=function(a,b){this.wb[a]=b;var c=fcl[fcA](b).className;if(!c&&c[fct]==0)fcfa(fcl[fcA](b),"gadgets-gadget-container")};fcSc[fc].Ma=function(a){return(a=this.wb[a.id])?fcl[fcA](a):fcc};var fc9=function(a){fc7[fcH](this,a);a=a||{};this.Z=a.view||"profile"};fcY(fc9,fc7);fc9[fc].nb="canvas.html";fc9[fc].ub="/friendconnect/embed/";
var fcQc=function(){var a=fce[fcM][fcD]().canvas=="1"||fce[fcM][fcD]().embed=="1",b=fcc;if(a)b=fce[fcM][fcD]().caller;if(!b){a=fcl[fcC];b=a.search[fcx](/([&?]?)psinvite=[^&]*(&?)/,function(c,d,e){return e?d:""});b=a.protocol+"//"+a.hostname+(a.port?":"+a.port:"")+a.pathname+b}return b};fc9[fc].wc=function(a){this.Z=a};fc9[fc].ka=function(){return this.Z};fc9[fc].getBodyId=function(){return this.ga()+"_body"};
fc9[fc].Gb=function(a){var b=this.specUrl;if(b===fcg)b="";b=(fc5.Oa(b)||this.U)+this.oc;var c=this.ga();fce.rpc.setRelayUrl(c,b);b='<div id="'+this.getBodyId()+'"><iframe id="'+c+'" name="'+c;b+=this[fcQ]==0?'" style="width:1px; height:1px;':'" style="width:100%;';if(this.viewParams.opaque)b+="background-color:white;";b+='"';b+=' frameborder="0" scrolling="no"';this.viewParams.opaque||(b+=' allowtransparency="true"');b+=this[fcQ]?' height="'+this[fcQ]+'"':"";b+=this[fcu]?' width="'+this[fcu]+'"':
"";b+="></iframe>";if(this.showEmbedThis)b+='<a href="javascript:void(0);" onclick="google.friendconnect.container.showEmbedDialog(\''+this.divId+"'); return false;\">Embed this</a>";b+="</div>";a(b)};
fc9[fc].zb=function(){var a=fcQc();a="canvas=1&caller="+fci(a);var b=fce[fcM][fcD]().psinvite;if(b)a+="&psinvite="+fci(b);a+="&site="+fci(this.communityId);b=fcIa(this.viewParams);if(b.skin!=fcc)for(var c=["BG_IMAGE","BG_COLOR","FONT_COLOR","BG_POSITION","BG_REPEAT","ANCHOR_COLOR","FONT_FACE","BORDER_COLOR","CONTENT_BG_COLOR","CONTENT_HEADLINE_COLOR","CONTENT_LINK_COLOR","CONTENT_SECONDARY_TEXT_COLOR","CONTENT_SECONDARY_LINK_COLOR","CONTENT_TEXT_COLOR","ENDCAP_BG_COLOR","ENDCAP_LINK_COLOR","ENDCAP_TEXT_COLOR",
"CONTENT_VISITED_LINK_COLOR","ALTERNATE_BG_COLOR"],d=0;d<c[fct];d++)delete b.skin[c[d]];b=fci(fce[fcpa][fcia](b));b=b[fcx]("\\","%5C");return fc5.S+this.nb+"?url="+fci(this.specUrl)+(a?"&"+a:"")+"&view-params="+b};fc9[fc].C=function(a){a=a||fch+this.ub+this.communityId;return this.Bb(a,"embed=1")};fc9[fc].B=function(a){return'<iframe src="'+this.C(a)+'" style="height:500px" scrolling="no" allowtransparency="true" border="0" frameborder="0" ></iframe>'};
fc9[fc].Bb=function(a,b){var c=fci(fce[fcpa][fcia](this.viewParams));c=c[fcx]("\\","%5C");return a+"?url="+fci(this.specUrl)+(b?"&"+b:"")+"&view-params="+c};fc9[fc].Kb=function(){var a=fce[fcM][fcD]().canvas=="1"||fce[fcM][fcD]().embed=="1",b=fcc;if(a)(b=fce[fcM][fcD]().caller)||(b="javascript:history.go(-1)");return b};fc9[fc].Lb=function(a){var b=fcc;if(a=="canvas")b=this.zb();else if(a=="profile")b=this.Kb();return b};
var fc$=function(){fcTc[fcH](this);fce.rpc[fcR]("signin",fc6[fc].signin);fce.rpc[fcR]("signout",fc6[fc].signout);fce.rpc[fcR]("resize_iframe",fc6[fc].ab);fce.rpc[fcR]("set_title",fc6[fc].setTitle);fce.rpc[fcR]("requestNavigateTo",fc6[fc].Za);fce.rpc[fcR]("api_loaded",fc6[fc].xa);fce.rpc[fcR]("createFriendBarMenu",fc6[fc].Ca);fce.rpc[fcR]("showFriendBarMenu",fc6[fc].db);fce.rpc[fcR]("hideFriendBarMenu",fc6[fc].Ra);fce.rpc[fcR]("putReloadViewParam",fc6[fc].Va);fce.rpc[fcR]("getViewParams",fc6[fc].Fa);
fce.rpc[fcR]("getContainerBaseTime",fc6[fc].Ea);fce.rpc[fcR]("openLightboxIframe",fc6[fc].Ua);fce.rpc[fcR]("showMemberProfile",fc6[fc].fb);fce.rpc[fcR]("closeLightboxIframe",fcW(this.u,this));fce.rpc[fcR]("setLightboxIframeTitle",fcW(this.sc,this));fce.rpc[fcR]("refreshAndCloseIframeLightbox",fcW(this.cc,this));var a=fcUc;a[fcR]();a.gb(this,"load",this.Nb);a.gb(this,"start",this.Ob);this.U="../../";this.W("");this.bb(0);this.Ga(1);this.oa=fcc;this.apiVersion="0.8";this.openSocialSecurityToken=fcc;
this.V="";this.Da={};this.Tb=fcc;this.Sb=fcd;this.sb=this.Wb=this.lastIframeLightboxOpenArguments=this.lastLightboxCallback=this.lastLightboxDialog=fcc;this.Fc="post"};fcY(fc$,fcTc);fc$[fc].qc=function(a){this.sb=a};fc$[fc].v=fc9;fc$[fc].R={};fc$[fc].uc=function(a){this.oa=a};fc$[fc].Oa=function(a){var b=fc$[fc].R[a];if(!b)if(this.oa[fcE]("http://")!==0){a=this.pb(a);b=["http://",a,this.oa][fcS]("")}else b=this.oa;return b};
fc$[fc].pb=function(a){var b=new SHA1;a=fcOa(a);b.update(a);b=b.digest();return b=fcJc(b)};
var fcVc=function(a,b){var c=b?b:fck.top,d=c.frames;try{if(c.frameElement.id==a)return c}catch(e){}for(c=0;c<d[fct];++c){var g=fcVc(a,d[c]);if(g)return g}return fcc},fcRc=function(a,b,c,d,e,g,i){var j="gfc_load_"+a;b='<html><head><style type="text/css">body {background:transparent;}</style>'+(fcZ?'<script type="text/javascript">window.goback=function(){history.go(-1);};setTimeout("goback();", 0);<\/script>':"")+"</head><body><form onsubmit='window.goback=function(){};return false;' style='margin:0;padding:0;' id='"+
j+"' method='"+c+"' ' action='"+fce[fcM].escapeString(b)+"'>";for(var k in d)b+="<input type='hidden' name='"+k+"' value='' >";b+="</form></body></html>";c=fcVc(a);var l;try{l=c[fcy]||c.contentWindow[fcy]}catch(h){if(e&&g){fcp(e,"");fcp(e,g);c=fcVc(a);l=c[fcy]||c.contentWindow[fcy]}}i&&fce.rpc.setAuthToken(a,i);l.open();l.write(b);l.close();a=l[fcA](j);for(k in d)a[k].value=d[k];fcZ&&a.onsubmit();a.submit()};
fc$[fc].vb=function(){var a=fce[fcM][fcD]().fcsite,b=fce[fcM][fcD]().fcprofile;a&&b&&fc5.I(b,a)};fc$[fc].rc=function(a,b){this.R[a]=b};fc$[fc].T=function(){var a=/Version\/3\..*Safari/;if(a=fcib&&fcdb()[fcI](a))fcl[fcC].reload();else{fc5.g!=fcc&&fc5.g.refresh();for(var b in fc5.j){a=fc5.j[b];this.H(a)}if(this.lastIframeLightboxOpenArguments!=fcc){b=this.lastIframeLightboxOpenArguments;this.u();this.F[fcN](this,b)}}};
fc$[fc].W=function(a){a[fcI](/^http[s]?:\/\//)||(a=a&&a[fct]>0&&a.substring(0,1)=="/"?fcl[fcC][fcsa][fcI](/^http[s]?:\/\/[^\/]+\//)[0]+a.substring(1):fcl[fcC][fcsa][fcI](/^[^?#]+\//)[0]+a);this.S=a};fc$[fc].ea=function(a){return"fcauth"+a};fc$[fc].ia=function(a){return"fcauth"+a+"-s"};fc$[fc].hash=function(a,b){var c=new SHA1,d=fcVa(a,fcb);c=new G_HMAC(c,d,64);d=fcOa(b);c=c.Fb(d);(new Date).getTime();return fcUa(c,fcb)};
fc$[fc].Ja=function(a){return a=fcvb(this.ea(a))||fcvb(this.ia(a))||this.Da[a]||""};fc$[fc].X=function(a){this.U=a};fc$[fc].vc=function(a){this.V=a};fc$[fc].M=function(a){a=new this.v(a);a.X(this.U);return a};fc$[fc].ka=function(){return this.Z};fc$[fc].tc=function(a){this.Wb=a};var fcWc=function(a){return(a=a[fcI](/_([0-9]+)$/))?fcaa(a[1],10):fcc};
fc$[fc].Y=function(a,b,c,d,e,g){if(!this.Dc){this.$(fck.friendconnect_serverBase+"/friendconnect/styles/container.css?d="+this.V);this.Dc=fcb}var i=fcXc(d);if(this.Tb!=(i?"rtl":"ltr")){this.$(fck.friendconnect_serverBase+"/friendconnect/styles/lightbox"+(i?"-rtl":"")+".css?d="+this.V);this.Tb=i?"rtl":"ltr"}if(!this.Sb){this.mb(fck.friendconnect_serverBase+"/friendconnect/script/lightbox.js?d="+this.V);this.Sb=fcb}b=b||0;if(goog.ui&&goog.ui[fcma]){this.u();b=new goog.ui[fcma]("lightbox-dialog",fcb);
var j=this;goog.events.listen(b,goog.ui[fcma].EventType.AFTER_HIDE,function(){j.lastLightboxCallback&&j.lastLightboxCallback();j.Ba()});b.setDraggable(fcb);b.setDisposeOnHide(fcb);b.setBackgroundElementOpacity(0.5);b.setButtonSet(new goog.ui[fcma].ButtonSet);this.lastLightboxDialog=b;this.lastLightboxCallback=c||fcc;c=b.getDialogElement();e=e||702;fcac(c,"width",fcj(e)+"px");g&&fcac(c,"height",fcj(g)+"px");a(b);b.getDialogElement()[fcF].direction=i?"rtl":"ltr"}else if(b<5){b++;a=fcW(this.Y,this,a,
b,c,d,e,g);fcba(a,1E3)}else{this.Ba();fca(fcf("lightbox.js failed to load"))}};fc$[fc].u=function(a){var b=this.lastLightboxDialog,c=this.lastLightboxCallback;this.lastLightboxCallback=fcc;if(b!=fcc){this.lastLightboxDialog.dispatchEvent(goog.ui[fcma].EventType.AFTER_HIDE);b.dispose();c!=fcc&&c(a)}};fc$[fc].Ba=function(){this.lastIframeLightboxOpenArguments=this.lastLightboxCallback=this.lastLightboxDialog=fcc};fc$[fc].sc=function(a){this.lastLightboxDialog&&this.lastLightboxDialog.setTitle(a)};
fc$[fc].cc=function(){this.u();this.T()};fc6[fc].Za=function(a,b){var c=fcWc(this.f);c=fc5.w(c);var d=fcIa(c.originalParams);if(b){d["view-params"]=d["view-params"]||{};d["view-params"]=b}d.locale=c.locale;if(c.useLightBoxForCanvas){d.presentation=a;fc5.lastLightboxDialog!=fcc?fc5.u():fc5.eb(d)}else if((c=c.Lb(a))&&fcl[fcC][fcsa]!=c)if(fce[fcM][fcD]().embed=="1")try{fck.parent.location=c}catch(e){fck.top.location=c}else fcl[fcC].href=c};
fc$[fc].eb=function(a,b){a=a||{};var c=a.locale,d=fcXc(c),e=this;this.u();this.Y(function(g){var i=fc1("div",{},fc1("div",{id:"gadget-signin",style:"background-color:#ffffff;height:32px;"}),fc1("div",{id:"gadget-lb-canvas",style:"background-color:#ffffff;"}));g.getTitleTextElement()[fcr](fc1("div",{id:"gfc-canvas-title",style:"color:#000000;"}));g.getContentElement()[fcr](i);g.setVisible(fcb);i=fcIa(a);var j=fcWb(fck),k=fcn[fcv](j[fcQ]*0.7);j={};j.BORDER_COLOR="#cccccc";j.ENDCAP_BG_COLOR="#e0ecff";
j.ENDCAP_TEXT_COLOR="#333333";j.ENDCAP_LINK_COLOR="#0000cc";j.ALTERNATE_BG_COLOR="#ffffff";j.CONTENT_BG_COLOR="#ffffff";j.CONTENT_LINK_COLOR="#0000cc";j.CONTENT_TEXT_COLOR="#333333";j.CONTENT_SECONDARY_LINK_COLOR="#7777cc";j.CONTENT_SECONDARY_TEXT_COLOR="#666666";j.CONTENT_HEADLINE_COLOR="#333333";i.id="gadget-lb-canvas";fcq(i,fcn.min(498,k)+"px");i.maxHeight=k;if(i.keepMax){fcq(i,k);fcac(g.getContentElement(),"height",k+35+"px")}i["view-params"]=i["view-params"]||{};i["view-params"].opaque=fcb;i["view-params"].skin=
i["view-params"].skin||{};fcMa(i["view-params"].skin,j);e.render(i);k={};k.id="gadget-signin";k.presentation="canvas";k.site=i.site;k.titleDivId="gfc-canvas-title";k["view-params"]={};k["view-params"].opaque=fcb;k.keepMax=i.keepMax;if(i.securityToken)k.securityToken=i.securityToken;i=fcIa(j);i.ALIGNMENT=d?"left":"right";e.Xa(k,i);g.reposition()},fcg,b,c)};fc6[fc].db=function(a,b){fc5.g!=fcc&&fc5.g.yc(a,b)};fc6[fc].Ra=function(a){fc5.g!=fcc&&fc5.g.la(a)};
fc6[fc].Ua=function(a,b,c,d,e,g,i,j,k,l){var h=this.f;a=a+(a[fcE]("?")>=0?"&":"?")+"iframeId="+h;fc5.F(a,b,c,d,e,g,i,j,k,l,this.callback)};
fc$[fc].F=function(a,b,c,d,e,g,i,j,k,l,h){var f=fcWb(fck);d!=fcc||(d=fcn[fcv](f[fcQ]*0.7));c!=fcc||(c=fcn[fcv](f[fcu]*0.7));var m=[];for(f=0;f<arguments[fct]&&f<10;f++)m[fcs](arguments[f]);if(!a[0]=="/")fca(fcf("lightbox iframes must be relative to fc server"));var n=this,o=g?fcIa(g):{},s=fcj(fcn[fcv](2147483647*fcn[fcoa]())),p="gfc_lbox_iframe_"+s;fce.rpc.setAuthToken(p,s);if(!b)b=fc5.openSocialSecurityToken;var t=fc5.openSocialSiteId;fc5.Y(function(q){n.lastIframeLightboxOpenArguments=m;var v="st="+
fci(b)+"&parent="+fci(fc5.S)+"&rpctoken="+fci(s);if(!j){o.iframeId=p;o.iurl=a;a=fch+"/friendconnect/lightbox"}var r=d-54;fcq(o,r);var u='<iframe id="'+p;u+='" width="100%" height="'+r+'" frameborder="0" scrolling="auto"></iframe>';q.setContent(u);if(e){q.setTitle(e);if(l){r=q.getTitleTextElement();fcQb(r,"lightbox-dialog-title-small-text")}}q.setVisible(fcb);k||(o.fcauth=fc5.Ja(t));a+=(a[fcE]("?")>=0?"&":"?")+v+"&communityId="+t;fcRc(p,a,"POST",o,fcc,fcc,fcc)},fcg,h,fcg,c,d)};
fc6[fc].Fa=function(){var a=fcWc(this.f);a=fc5.w(a);return a.viewParams};fc6[fc].Ea=function(){return fcLc};fc6[fc].Va=function(a,b){var c=fcWc(this.f);c=fc5.w(c);c.viewParams[a]=b};fc$[fc].Nb=function(a,b){fc5.g!=fcc&&fc5.g.Xb(b)};fc$[fc].Ob=function(a,b){fc5.g!=fcc&&fc5.g.Yb(b)};fc6[fc].Ca=function(a,b,c,d){fc5.g!=fcc&&fc5.g.rb(a,b,c,d)};fc$[fc].H=function(a){var b=this.A.Ma(a);a.render(b);this.A.postProcessGadget&&this.A.postProcessGadget(a)};
fc6[fc].signout=function(a){fc5.Wa(fc5.ea(a));fc5.Wa(fc5.ia(a));fc5.Da={};fc5.T();return fcd};fc$[fc].Wa=function(a){var b=fcl[fcC].pathname;b=b[fcz]("/");for(var c=0;c<b[fct];c++){for(var d=fcm(c+1),e=0;e<c+1;e++)d[e]=b[e];fcwb(a,d[fcS]("/")+"/")}};
fc6[fc].ab=function(a){var b=fcl[fcA](this.f);if(b&&a>0)fcq(b[fcF],a+"px");if((b=fcl[fcA](this.f+"_body"))&&a>0)fcq(b[fcF],a+"px");if(b=fcWc(this.f)){var c=fc5.w(b);if(c){if((b=fcl[fcA](c.divId))&&a>0){if(c&&c[fcta]&&c[fcta]<a){a=c[fcta];b[fcF].overflowY="auto"}fcq(b[fcF],a+"px")}!c.keepMax&&c.ka()=="canvas"&&fc5.lastLightboxDialog&&fc5.lastLightboxDialog.reposition();fcac(c.chrome,"visibility","visible")}}};
fc6[fc].setTitle=function(a){var b=fcWc(this.f);b=fc5.w(b);if(b=b.titleDivId)fcp(fcl[fcA](b),fce[fcM].escapeString(a))};fc6[fc].signin=function(a,b,c){fcub(fc5.ea(a),b,31104E3,c);fcub(fc5.ia(a),b,-1,c);fc5.Da[a]=b;fc5.T()};var fcZc=function(a){fczc(a,fcYc)};fc$[fc].ic=function(a,b){b&&this.m(b,a);var c={};c.url=fch+"/friendconnect/gadgets/members.xml";this.render(this.s(a,c))};
fc$[fc].kc=function(a,b){b&&this.m(b,a);var c={};c.url=fch+"/friendconnect/gadgets/review.xml";c["view-params"]={startMaximized:"true",disableMinMax:"true",features:"review"};this.render(this.s(a,c))};fc$[fc].ra=function(a,b){b&&this.m(b,a);var c={};c.url=fch+"/friendconnect/gadgets/wall.xml";c["view-params"]={startMaximized:"true",disableMinMax:"true",features:"comment"};this.render(this.s(a,c))};
fc$[fc].Xa=function(a,b){b&&this.m(b,a);var c={};c.url=fch+"/friendconnect/gadgets/signin.xml";fcq(c,32);this.render(this.s(a,c))};fc$[fc].fc=function(a,b){b&&this.m(b,a);a.prefs=a.prefs||{};a.sendViewParamsToServer=fcb;a.prefs.hints=fck.google_hints;var c={};c.url=fch+"/friendconnect/gadgets/ads.xml";fcq(c,90);this.render(this.s(a,c))};
fc$[fc].ua=function(a,b){if(a.id){b&&this.m(b,a);a["view-params"]=a["view-params"]||{};a["view-params"].opaque="true";this.g=new fc3(a);this.g.render();var c={};c.url=fch+"/friendconnect/gadgets/friendbar.xml";a.id=this.g.t;fcq(a,"1");this.render(this.s(a,c))}};fc$[fc].hc=fc$[fc].ua;fc$[fc].ta=function(a,b){a=a||{};a.url=fch+"/friendconnect/gadgets/signin.xml";a.site=a.site||fce[fcM][fcD]().site;fcq(a,32);this.sa(a,b)};fc$[fc].gc=fc$[fc].ta;fc$[fc].mc=fc$[fc].ra;
fc$[fc].m=function(a,b){var c=b["view-params"];if(!c){c={};b["view-params"]=c}c.skin=a};fc$[fc].s=function(a,b){var c=this.Ta(b,a);if(b["view-params"]){var d=b["view-params"];if(a["view-params"])d=this.Ta(d,a["view-params"]);c["view-params"]=d}return c};fc$[fc].jc=function(a,b){b&&this.m(b,a);this.render(a)};fc$[fc].Ta=function(a,b){var c={},d;for(d in b)c[d]=b[d];for(d in a)if(typeof c[d]=="undefined")c[d]=a[d];return c};
fc$[fc].render=function(a){this.openSocialSiteId=a.site;a["view-params"]=a["view-params"]||{};var b=this.M({divId:a.id,specUrl:a.url,communityId:a.site,height:a[fcQ],locale:a.locale||this.Wb,secureToken:a.securityToken,titleDivId:a.titleDivId,showEmbedThis:a.showEmbedThis,useLightBoxForCanvas:a.useLightBoxForCanvas||typeof a.useLightBoxForCanvas=="undefined"&&fck.friendconnect_lightbox,viewParams:a["view-params"],prefs:a.prefs,originalParams:a,debug:a.debug,maxHeight:a[fcta],sendViewParamsToServer:a.sendViewParamsToServer,
keepMax:a.keepMax});a.presentation&&b.wc(a.presentation);this.kb(b);this.A.lb(b.id,a.id);fcba(function(){fc5.H(b)},0);return b.id};fc$[fc].lc=function(a,b){a=a||{};a.presentation="canvas";this.Ya(a,b)};
fc$[fc].Ya=function(a,b,c){a=a||{};a.url=fce[fcM][fcD]().url;a.site=fce[fcM][fcD]().site||a.site;var d=fce[fcM][fcD]()["view-params"];if(d)a["view-params"]=fce[fcpa].parse(decodeURIComponent(d));if(c){a["view-params"]=a["view-params"]||{};a["view-params"].useFixedHeight=fcb;fcq(a["view-params"],c);b=b||{};b.HEIGHT=fcj(c)}this.sa(a,b)};fc$[fc].sa=function(a,b){a=a||{};b&&this.m(b,a);if(fce[fcM][fcD]().canvas=="1")a.presentation="canvas";else if(fce[fcM][fcD]().embed=="1")a.presentation="embed";fc5.render(a)};
fc$[fc].Mb=function(){var a=fce[fcM][fcD]().caller;if(a&&fcl[fcC][fcsa]!=a&&a[fct]>8&&(a.substr(0,7)[fcCa]()=="http://"||a.substr(0,8)[fcCa]()=="https://"))fcl[fcC].href=a;else if(a=fce[fcM][fcD]().site)fcl[fcC].href=fch+"/friendconnect/directory/site?id="+a;else fck.history.go(-1)};fc$[fc].G="";fc$[fc].Ib=function(){return this.G};fc$[fc].pc=function(a){this.apiVersion=a};fc$[fc].$=function(a){var b=fcl[fcK]("link");b[fcL]("rel","stylesheet");b[fcL]("type","text/css");b[fcL]("href",a);fcl.getElementsByTagName("head")[0][fcr](b)};
fc$[fc].mb=function(a){var b=fcl[fcK]("script");b[fcL]("src",a);b[fcL]("type","text/javascript");fcl.getElementsByTagName("head")[0][fcr](b)};fc$[fc].Aa=function(a){if(fcl[fcna])a();else fck[fcqa]?fck[fcqa]("load",a,fcd):fck.attachEvent("onload",a)};
fc$[fc].ma=function(a){if(!a.site)fca("API not loaded, please pass in a 'site'");this.$(fck.friendconnect_serverBase+"/friendconnect/styles/container.css?d="+this.V);this.openSocialSiteId=a.site;this.apiLoadedCallback=a.onload;this.Aa(fcW(this.Sa,this,a,"fc-opensocial-api"))};fc$[fc].Vb=fc$[fc].ma;fc$[fc].Pb=function(a){var b={};b.site=this.openSocialSiteId;b["view-params"]={txnId:a};this.Sa(b,"gfc-"+a)};
fc$[fc].ec=function(a){var b={};for(var c in this.j){var d=this.j[c];if(d.viewParams&&d.viewParams.txnId==a)break;else b[c]=d}this.j=b;(a=fcl[fcA]("gfc-"+a))&&a[fcP]&&a[fcP].removeChild&&a[fcP].removeChild(a)};fc$[fc].Cb=function(){return"<Templates xmlns:fc='http://www.google.com/friendconnect/makeThisReal'>  <Namespace prefix='fc' url='http://www.google.com/friendconnect/makeThisReal'/>  <Template tag='fc:signIn'>    <div onAttach='google.friendconnect.renderSignInButton({element: this})'></div>  </Template></Templates>"};
fc$[fc].Jb=function(){return"<Templates xmlns:os='http://ns.opensocial.org/2008/markup'><Namespace prefix='os' url='http://ns.opensocial.org/2008/markup'/><Template tag='os:Name'>  <span if='${!My.person.profileUrl}'>${My.person.displayName}</span>  <a if='${My.person.profileUrl}' href='${My.person.profileUrl}'>      ${My.person.displayName}</a></Template><Template tag='os:Badge'>  <div><img if='${My.person.thumbnailUrl}' src='${My.person.thumbnailUrl}'/>   <os:Name person='${My.person}'/></div></Template><Template tag='os:PeopleSelector'>  <select onchange='google.friendconnect.PeopleSelectorOnChange(this)' name='${My.inputName}'          multiple='${My.multiple}' x-var='${My.var}' x-max='${My.max}'          x-onselect='${My.onselect}'>    <option repeat='${My.group}' value='${Cur.id}' selected='${Cur.id == My.selected}'>        ${Cur.displayName}    </option>  </select></Template></Templates>"};
var fc_c=function(a){var b;if(a.multiple){b=[];for(var c=0;c<a[fcJ][fct];c++)a[fcJ][c].selected&&b[fcs](a[fcJ][c].value);c=a.getAttribute("x-max");try{c=1*c}catch(d){c=0}if(c&&b[fct]>c&&a["x-selected"]){b=a["x-selected"];for(c=0;c<a[fcJ][fct];c++){a[fcJ][c].selected=fcd;for(var e=0;e<b[fct];e++)if(a[fcJ][c].value==b[e]){a[fcJ][c].selected=fcb;break}}}}else b=a[fcJ][a.selectedIndex].value;a["x-selected"]=b;(c=a.getAttribute("x-var"))&&fck.opensocial[fcka]&&fck.opensocial[fcka].getDataContext().putDataSet(c,
b);if(c=a.getAttribute("x-onselect"))if(fck[c]&&typeof fck[c]=="function")fck[c](b);else if(a["x-onselect-fn"])a["x-onselect-fn"][fcN](a);else a["x-onselect-fn"]=new Function(c)};
fc$[fc].Sa=function(a,b){fck.opensocial.template.Loader.loadContent(this.Jb());fck.opensocial.template.Loader.loadContent(this.Cb());fck.opensocial[fcka].processDocumentMarkup();var c=fcl[fcK]("div");c.id=b;fcq(c[fcF],"0px");fco(c[fcF],"0px");c[fcF].position="absolute";c[fcF].visibility="hidden";fcl[fcna][fcr](c);var d={};d.url=fch+"/friendconnect/gadgets/osapi-"+this.apiVersion+".xml";fcq(d,0);d.id=c.id;d.site=a.site;d["view-params"]=a["view-params"];this.render(d)};
fc6[fc].xa=function(){fc5.G=this.f;fc5.openSocialSecurityToken=this.a[0];var a=fc5.openSocialSecurityToken;fck.opensocial[fcka].executeRequests();fck.opensocial.template.process();if(fc5.apiLoadedCallback){a=fcLa(fc5.apiLoadedCallback,a);fcba(a,0)}};fc$[fc].O=function(a){var b=fcc;for(var c in this.j)if(this.j[c].divId==a){b=this.j[c];break}return b};fc$[fc].C=function(a,b){var c=this.O(a),d=fcc;if(c)d=c.C(b);return d};fc$[fc].B=function(a,b){var c=this.O(a),d=fcc;if(c)d=c.B(b);return d};
fc$[fc].xc=function(a,b){this.Y(function(c){var d=fcl.createTextNode("Copy & paste this code into your site.");c.getContentElement()[fcr](d);c.getContentElement()[fcr](fcl[fcK]("br"));d=fc5.B(a,b);var e=fcl[fcK]("textarea");fcp(e,d);e[fcL]("style","width:500px;");c.getContentElement()[fcr](e);c.setVisible(fcb)})};
var fc0c=["ar","dv","fa","iw","he","ku","pa","sd","tk","ug","ur","yi"],fcXc=function(a){a=a;var b=fcd;if(a){a=a[fcz]("_")[0];b=fcDb(fc0c,a)}else b=(a=fccc(fcl[fcna],"direction"))&&a=="rtl";return b};fc6[fc].fb=function(a,b){var c=0,d=fcc;try{var e=fcWc(this.f),g=fc5.w(e);d=g.secureToken;c=g.communityId}catch(i){}if(b)c=b;fc5.I(a,c,this.callback,d)};
fc$[fc].I=function(a,b,c,d){b=b||this.openSocialSiteId;a={keepMax:fcb,presentation:"canvas",url:fch+"/friendconnect/gadgets/members.xml",site:b,"view-params":{profileId:a}};if(d)a.securityToken=d;this.eb(a,c)};fc$[fc].Eb=function(a){var b=fcc;if((a=this.O(a))&&a.secureToken)b=a.secureToken;return b};fc$[fc].Db=function(a){var b=fcc;if((a=this.O(a))&&a.communityId)b=a.communityId;return b};
var fcYc=function(a){fc5.G&&fctc(fc5.G,a)},fc1c=function(){fc6[fc].signout(fc5.openSocialSiteId)},fc2c=function(){fcwc(fc5.G)},fc3c=function(a,b){fcqc(a,b)},fcFc=function(){this.o={}};fcFc[fc].register=function(){fce.rpc[fcR]("subscribeEventType",fc6[fc].subscribe);fce.rpc[fcR]("publishEvent",fc6[fc].publish)};fc6[fc].subscribe=function(a){var b=fcUc;b.o[a]=b.o[a]||[];a=b.o[a];a[a[fct]]={frameId:this}};fcFc[fc].gb=function(a,b,c){var d=this;d.o[b]=d.o[b]||[];b=d.o[b];b[b[fct]]={container:a,callback:c}};
fc6[fc].publish=function(a){var b=fcUc,c=0;if(this.f)c=fcWc(this.f);b.o[a]=b.o[a]||[];b=b.o[a];for(var d=0;d<b[fct];d++)b[d].container?b[d].callback[fcH](b[d].container,a,c):fce.rpc[fcH](b[d].frameId,a,fcc,a,c)};var fcGc=fcW(fc6[fc].publish,new fc6),fcUc=new fcFc,fc5=new fc$;fc5.Aa(fc5.vb);fcX("google.friendconnect.container",fc5);fcX("google.friendconnect.container.refreshGadgets",fc5.T);fcX("google.friendconnect.container.setParentUrl",fc5.W);fcX("google.friendconnect.container.setServerBase",fc5.X);
fcX("google.friendconnect.container.setServerVersion",fc5.vc);fcX("google.friendconnect.container.createGadget",fc5.M);fcX("google.friendconnect.container.openLightboxIframe",fc5.F);fcX("google.friendconnect.container.renderGadget",fc5.H);fcX("google.friendconnect.container.render",fc5.render);fcX("google.friendconnect.container.goBackToSite",fc5.Mb);fcX("google.friendconnect.container.renderMembersGadget",fc5.ic);fcX("google.friendconnect.container.renderReviewGadget",fc5.kc);
fcX("google.friendconnect.container.renderCommentsGadget",fc5.ra);fcX("google.friendconnect.container.renderSignInGadget",fc5.Xa);fcX("google.friendconnect.container.renderFriendBar",fc5.hc);fcX("google.friendconnect.container.renderSocialBar",fc5.ua);fcX("google.friendconnect.container.renderCanvasSignInGadget",fc5.gc);fcX("google.friendconnect.container.renderUrlCanvasGadget",fc5.lc);fcX("google.friendconnect.container.renderEmbedSignInGadget",fc5.ta);
fcX("google.friendconnect.container.renderUrlEmbedGadget",fc5.Ya);fcX("google.friendconnect.container.renderEmbedGadget",fc5.sa);fcX("google.friendconnect.container.renderWallGadget",fc5.mc);fcX("google.friendconnect.container.renderAdsGadget",fc5.fc);fcX("google.friendconnect.container.renderOpenSocialGadget",fc5.jc);fcX("google.friendconnect.container.setNoCache",fc5.bb);fcX("google.friendconnect.container.enableProxy",fc5.Ga);fcX("google.friendconnect.container.setDomain",fc5.rc);
fcX("google.friendconnect.container.setLockedDomainSuffix",fc5.uc);fcX("google.friendconnect.container.setLocale",fc5.tc);fcX("google.friendconnect.container.loadOpenSocialApi",fc5.Vb);fcX("google.friendconnect.container.initOpenSocialApi",fc5.ma);fcX("google.friendconnect.container.getOpenSocialApiIframeId",fc5.Ib);fcX("google.friendconnect.container.setApiVersion",fc5.pc);fcX("google.friendconnect.container.getEmbedUrl",fc5.C);fcX("google.friendconnect.container.getEmbedHtml",fc5.B);
fcX("google.friendconnect.container.getGadgetSecurityToken",fc5.Eb);fcX("google.friendconnect.container.getGadgetCommunityId",fc5.Db);fcX("google.friendconnect.container.showEmbedDialog",fc5.xc);fcX("google.friendconnect.container.showMemberProfile",fc5.I);fcX("google.friendconnect.requestSignIn",fcYc);fcX("google.friendconnect.requestSignOut",fc1c);fcX("google.friendconnect.requestSettings",fc2c);fcX("google.friendconnect.requestInvite",fc3c);fcX("google.friendconnect.renderSignInButton",fcZc);
fcX("google.friendconnect.container.invokeOpenSocialApiViaIframe",fc5.Pb);fcX("google.friendconnect.container.removeOpenSocialApiViaIframe",fc5.ec);fcX("google.friendconnect.userAgent.WEBKIT",fcib);fcX("google.friendconnect.userAgent.IE",fcZ);fcX("google.friendconnect.PeopleSelectorOnChange",fc_c);fcX("google.friendconnect.container.setDateStamp_",fc5.qc);
google.friendconnect.container.setServerBase('http://www-a-fc-opensocial.googleusercontent.com/ps/');google.friendconnect.container.setServerVersion('0.554.6');google.friendconnect.container.setApiVersion('0.8');
google.friendconnect.container.setDomain('http://www.google.com/friendconnect/gadgets/activities.xml', 'http://q8j0igk2u2f6kf7jogh6s66md2d7r154-a-fc-opensocial.googleusercontent.com/ps/');

google.friendconnect.container.setDomain('http://www.google.com/friendconnect/gadgets/ads.xml', 'http://t767uouk8skpac15v8ue0n16regb3m2t-a-fc-opensocial.googleusercontent.com/ps/');

google.friendconnect.container.setDomain('http://www.google.com/friendconnect/gadgets/ask.xml', 'http://uofgf6lm45rimd9jp6hn983ul6n2en81-a-fc-opensocial.googleusercontent.com/ps/');

google.friendconnect.container.setDomain('http://www.google.com/friendconnect/gadgets/friendbar.xml', 'http://p7rjrrl49ose4gob99eonlvp0drmce3d-a-fc-opensocial.googleusercontent.com/ps/');

google.friendconnect.container.setDomain('http://www.google.com/friendconnect/gadgets/content_reveal.xml', 'http://n0mc7q283f00tpk3uifa7sjv4hmrults-a-fc-opensocial.googleusercontent.com/ps/');

google.friendconnect.container.setDomain('http://www.google.com/friendconnect/gadgets/chat.xml', 'http://4mmefl67as0q39gf1o4pnocubqmdgei0-a-fc-opensocial.googleusercontent.com/ps/');

google.friendconnect.container.setDomain('http://www.google.com/friendconnect/gadgets/donate.xml', 'http://7v4nflqvq38notsghmcr5a9t6o9g6kn4-a-fc-opensocial.googleusercontent.com/ps/');

google.friendconnect.container.setDomain('http://www.google.com/friendconnect/gadgets/lamegame.xml', 'http://ffbrstu9puk7qmt45got9mqe5k7ijrs4-a-fc-opensocial.googleusercontent.com/ps/');

google.friendconnect.container.setDomain('http://www.google.com/friendconnect/gadgets/map.xml', 'http://k0dfp8trn0hi5d2spmo7jmc88n6kr1cc-a-fc-opensocial.googleusercontent.com/ps/');

google.friendconnect.container.setDomain('http://www.google.com/friendconnect/gadgets/members.xml', 'http://r1rk9np7bpcsfoeekl0khkd2juj27q3o-a-fc-opensocial.googleusercontent.com/ps/');

google.friendconnect.container.setDomain('http://www.google.com/friendconnect/gadgets/newsletterSubscribe.xml', 'http://k830suiki828goudg9448o6bp0tpu5r3-a-fc-opensocial.googleusercontent.com/ps/');

google.friendconnect.container.setDomain('http://www.google.com/friendconnect/gadgets/poll.xml', 'http://1ivjd75aqp679vbgohjv74tlhn13rgdu-a-fc-opensocial.googleusercontent.com/ps/');

google.friendconnect.container.setDomain('http://www.google.com/friendconnect/gadgets/recommended_pages.xml', 'http://iqavu79a908u5vcecp0pq80hhbhkv33b-a-fc-opensocial.googleusercontent.com/ps/');

google.friendconnect.container.setDomain('http://www.google.com/friendconnect/gadgets/review.xml', 'http://r85jiaoot111joesr8bilfhfeslcc496-a-fc-opensocial.googleusercontent.com/ps/');

google.friendconnect.container.setDomain('http://www.google.com/friendconnect/gadgets/sample.xml', 'http://785aoao97uti9iqffknjp5e0kn2ljlm4-a-fc-opensocial.googleusercontent.com/ps/');

google.friendconnect.container.setDomain('http://www.google.com/friendconnect/gadgets/signin.xml', 'http://8fkcem1ves287v3g5lu9gep1j91p3kk1-a-fc-opensocial.googleusercontent.com/ps/');

google.friendconnect.container.setDomain('http://www.google.com/friendconnect/gadgets/wall.xml', 'http://o29lt44ell30t7ljcdfr8lq2mjakv2co-a-fc-opensocial.googleusercontent.com/ps/');

google.friendconnect.container.setDomain('http://www.google.com/friendconnect/gadgets/osapi-0.8.xml', 'http://mc8tdi0ripmbpds25eboaupdulritrp6-a-fc-opensocial.googleusercontent.com/ps/');

google.friendconnect.container.setLockedDomainSuffix('-a-fc-opensocial.googleusercontent.com/ps/');
window['__ps_loaded__'] = true; 
 }google.friendconnect_ = google.friendconnect;
(function(){if (!window['__fc_os_loaded__']) {var GLOBAL_navigator=navigator,GLOBAL_gadgets=gadgets,GLOBAL_Error=Error,GLOBAL_undefined=undefined,GLOBAL_window=window,GLOBAL_Object=Object,GLOBAL_document=document,GLOBAL_google=google,GLOBAL_Array=Array;function SETPROP_length(SETPROP_length$a,SETPROP_length$b){return SETPROP_length$a.length=SETPROP_length$b}function SETPROP_prototype(SETPROP_prototype$a,SETPROP_prototype$b){return SETPROP_prototype$a.prototype=SETPROP_prototype$b}
var $$PROP_appendChild="appendChild",$$PROP_push="push",$$PROP_object="object",$$PROP_toString="toString",$$PROP_global="global",$$PROP_length="length",$$PROP_propertyIsEnumerable="propertyIsEnumerable",$$PROP_container="container",$$PROP_prototype="prototype",$$PROP_slice="slice",$$PROP_setTimeout="setTimeout",$$PROP_replace="replace",$$PROP_nodeType="nodeType",$$PROP_data="data",$$PROP_split="split",$$PROP_getElementById="getElementById",$$PROP_constructor="constructor",$$PROP_charAt="charAt",$$PROP_createTextNode=
"createTextNode",$$PROP_getData="getData",$$PROP_value="value",$$PROP_indexOf="indexOf",$$PROP_hasOwnProperty="hasOwnProperty",$$PROP_friendconnect="friendconnect",$$PROP_removeChild="removeChild",$$PROP_call="call",$$PROP_match="match",$$PROP_send="send",$$PROP_remove="remove",$$PROP_getAttribute="getAttribute",$$PROP_createElement="createElement",$$PROP_firstChild="firstChild",$$PROP_setAttribute="setAttribute",$$PROP_substring="substring",$$PROP_cloneNode="cloneNode",$$PROP_util="util",$$PROP_type=
"type",$$PROP_apply="apply",$$PROP_childNodes="childNodes",$$PROP_tagName="tagName",$$PROP_bind="bind",$$PROP_attributes="attributes",$$PROP_removeAttribute="removeAttribute",$$PROP_parentNode="parentNode",$$PROP_nextSibling="nextSibling",$$PROP_join="join",$$PROP_unshift="unshift",$$PROP_getElementsByTagName="getElementsByTagName",$$PROP_execScript="execScript",$$PROP_nodeValue="nodeValue",$$PROP_toLowerCase="toLowerCase",$$PROP_substr="substr",a,goog=goog||{};goog.global=this;goog.DEBUG=true;
goog.LOCALE="en";goog.evalWorksForGlobals_=null;goog.provide=function(name){goog.exportPath_(name)};goog.setTestOnly=function(opt_message){if(!goog.DEBUG){opt_message=opt_message||"";throw GLOBAL_Error("Importing test-only code into non-debug environment"+opt_message?": "+opt_message:".");}};
goog.exportPath_=function(name,opt_object,opt_objectToExportTo){var parts=name[$$PROP_split]("."),cur=opt_objectToExportTo||goog[$$PROP_global];!(parts[0]in cur)&&cur[$$PROP_execScript]&&cur[$$PROP_execScript]("var "+parts[0]);for(var part;parts[$$PROP_length]&&(part=parts.shift());)if(!parts[$$PROP_length]&&goog.isDef(opt_object))cur[part]=opt_object;else cur=cur[part]?cur[part]:cur[part]={}};
goog.getObjectByName=function(name,opt_obj){for(var parts=name[$$PROP_split]("."),cur=opt_obj||goog[$$PROP_global],part;part=parts.shift();)if(cur[part])cur=cur[part];else return null;return cur};goog.globalize=function(obj,opt_global){var global=opt_global||goog[$$PROP_global];for(var x in obj)global[x]=obj[x]};goog.addDependency=function(){};goog.useStrictRequires=false;goog.require=function(){};goog.basePath="";goog.nullFunction=function(){};goog.identityFunction=function(){return arguments[0]};
goog.abstractMethod=function(){throw GLOBAL_Error("unimplemented abstract method");};goog.addSingletonGetter=function(ctor){ctor.getInstance=function(){return ctor.instance_||(ctor.instance_=new ctor)}};
goog.typeOf=function(value){var s=typeof value;if(s=="object")if(value){if(value instanceof GLOBAL_Array||!(value instanceof GLOBAL_Object)&&GLOBAL_Object[$$PROP_prototype][$$PROP_toString][$$PROP_call](value)=="[object Array]"||typeof value[$$PROP_length]=="number"&&typeof value.splice!="undefined"&&typeof value[$$PROP_propertyIsEnumerable]!="undefined"&&!value[$$PROP_propertyIsEnumerable]("splice"))return"array";if(!(value instanceof GLOBAL_Object)&&(GLOBAL_Object[$$PROP_prototype][$$PROP_toString][$$PROP_call](value)==
"[object Function]"||typeof value[$$PROP_call]!="undefined"&&typeof value[$$PROP_propertyIsEnumerable]!="undefined"&&!value[$$PROP_propertyIsEnumerable]("call")))return"function"}else return"null";else if(s=="function"&&typeof value[$$PROP_call]=="undefined")return"object";return s};
goog.propertyIsEnumerableCustom_=function(object,propName){if(propName in object)for(var key in object)if(key==propName&&GLOBAL_Object[$$PROP_prototype][$$PROP_hasOwnProperty][$$PROP_call](object,propName))return true;return false};goog.propertyIsEnumerable_=function(object,propName){return object instanceof GLOBAL_Object?GLOBAL_Object[$$PROP_prototype][$$PROP_propertyIsEnumerable][$$PROP_call](object,propName):goog.propertyIsEnumerableCustom_(object,propName)};
goog.isDef=function(val){return val!==GLOBAL_undefined};goog.isNull=function(val){return val===null};goog.isDefAndNotNull=function(val){return val!=null};goog.isArray=function(val){return goog.typeOf(val)=="array"};goog.isArrayLike=function(val){var type=goog.typeOf(val);return type=="array"||type=="object"&&typeof val[$$PROP_length]=="number"};goog.isDateLike=function(val){return goog.isObject(val)&&typeof val.getFullYear=="function"};goog.isString=function(val){return typeof val=="string"};
goog.isBoolean=function(val){return typeof val=="boolean"};goog.isNumber=function(val){return typeof val=="number"};goog.isFunction=function(val){return goog.typeOf(val)=="function"};goog.isObject=function(val){var type=goog.typeOf(val);return type=="object"||type=="array"||type=="function"};goog.getUid=function(obj){return obj[goog.UID_PROPERTY_]||(obj[goog.UID_PROPERTY_]=++goog.uidCounter_)};goog.removeUid=function(obj){"removeAttribute"in obj&&obj[$$PROP_removeAttribute](goog.UID_PROPERTY_);try{delete obj[goog.UID_PROPERTY_]}catch(ex){}};
goog.UID_PROPERTY_="closure_uid_"+Math.floor(Math.random()*2147483648)[$$PROP_toString](36);goog.uidCounter_=0;goog.getHashCode=goog.getUid;goog.removeHashCode=goog.removeUid;goog.cloneObject=function(obj){var type=goog.typeOf(obj);if(type=="object"||type=="array"){if(obj.clone)return obj.clone();var clone=type=="array"?[]:{};for(var key in obj)clone[key]=goog.cloneObject(obj[key]);return clone}return obj};goog.bindNative_=function(fn){return fn[$$PROP_call][$$PROP_apply](fn[$$PROP_bind],arguments)};
goog.bindJs_=function(fn,selfObj){var context=selfObj||goog[$$PROP_global];if(arguments[$$PROP_length]>2){var boundArgs=GLOBAL_Array[$$PROP_prototype][$$PROP_slice][$$PROP_call](arguments,2);return function(){var newArgs=GLOBAL_Array[$$PROP_prototype][$$PROP_slice][$$PROP_call](arguments);GLOBAL_Array[$$PROP_prototype][$$PROP_unshift][$$PROP_apply](newArgs,boundArgs);return fn[$$PROP_apply](context,newArgs)}}else return function(){return fn[$$PROP_apply](context,arguments)}};
goog.bind=function(){goog.bind=Function[$$PROP_prototype][$$PROP_bind]&&Function[$$PROP_prototype][$$PROP_bind][$$PROP_toString]()[$$PROP_indexOf]("native code")!=-1?goog.bindNative_:goog.bindJs_;return goog[$$PROP_bind][$$PROP_apply](null,arguments)};
goog.partial=function(fn){var args=GLOBAL_Array[$$PROP_prototype][$$PROP_slice][$$PROP_call](arguments,1);return function(){var newArgs=GLOBAL_Array[$$PROP_prototype][$$PROP_slice][$$PROP_call](arguments);newArgs[$$PROP_unshift][$$PROP_apply](newArgs,args);return fn[$$PROP_apply](this,newArgs)}};goog.mixin=function(target,source){for(var x in source)target[x]=source[x]};goog.now=Date.now||function(){return+new Date};
goog.globalEval=function(script){if(goog[$$PROP_global][$$PROP_execScript])goog[$$PROP_global][$$PROP_execScript](script,"JavaScript");else if(goog[$$PROP_global].eval){if(goog.evalWorksForGlobals_==null){goog[$$PROP_global].eval("var _et_ = 1;");if(typeof goog[$$PROP_global]._et_!="undefined"){delete goog[$$PROP_global]._et_;goog.evalWorksForGlobals_=true}else goog.evalWorksForGlobals_=false}if(goog.evalWorksForGlobals_)goog[$$PROP_global].eval(script);else{var doc=goog[$$PROP_global].document,scriptElt=
doc[$$PROP_createElement]("script");scriptElt.type="text/javascript";scriptElt.defer=false;scriptElt[$$PROP_appendChild](doc[$$PROP_createTextNode](script));doc.body[$$PROP_appendChild](scriptElt);doc.body[$$PROP_removeChild](scriptElt)}}else throw GLOBAL_Error("goog.globalEval not available");};goog.typedef=true;
goog.getCssName=function(className,opt_modifier){var cssName=className+(opt_modifier?"-"+opt_modifier:"");return goog.cssNameMapping_&&cssName in goog.cssNameMapping_?goog.cssNameMapping_[cssName]:cssName};goog.setCssNameMapping=function(mapping){goog.cssNameMapping_=mapping};goog.getMsg=function(str,opt_values){var values=opt_values||{};for(var key in values){var value=(""+values[key])[$$PROP_replace](/\$/g,"$$$$");str=str[$$PROP_replace](RegExp("\\{\\$"+key+"\\}","gi"),value)}return str};
goog.exportSymbol=function(publicPath,object,opt_objectToExportTo){goog.exportPath_(publicPath,object,opt_objectToExportTo)};goog.exportProperty=function(object,publicName,symbol){object[publicName]=symbol};goog.inherits=function(childCtor,parentCtor){function tempCtor(){}SETPROP_prototype(tempCtor,parentCtor[$$PROP_prototype]);childCtor.superClass_=parentCtor[$$PROP_prototype];SETPROP_prototype(childCtor,new tempCtor);childCtor[$$PROP_prototype].constructor=childCtor};
goog.base=function(me,opt_methodName){var caller=arguments.callee.caller;if(caller.superClass_)return caller.superClass_[$$PROP_constructor][$$PROP_apply](me,GLOBAL_Array[$$PROP_prototype][$$PROP_slice][$$PROP_call](arguments,1));for(var args=GLOBAL_Array[$$PROP_prototype][$$PROP_slice][$$PROP_call](arguments,2),foundCaller=false,ctor=me[$$PROP_constructor];ctor;ctor=ctor.superClass_&&ctor.superClass_[$$PROP_constructor])if(ctor[$$PROP_prototype][opt_methodName]===caller)foundCaller=true;else if(foundCaller)return ctor[$$PROP_prototype][opt_methodName][$$PROP_apply](me,
args);if(me[opt_methodName]===caller)return me[$$PROP_constructor][$$PROP_prototype][opt_methodName][$$PROP_apply](me,args);else throw GLOBAL_Error("goog.base called from a method of one name to a method of a different name");};goog.scope=function(fn){fn[$$PROP_call](goog[$$PROP_global])};goog.MODIFY_FUNCTION_PROTOTYPES=true;
if(goog.MODIFY_FUNCTION_PROTOTYPES){Function[$$PROP_prototype].bind=Function[$$PROP_prototype][$$PROP_bind]||function(selfObj){if(arguments[$$PROP_length]>1){var args=GLOBAL_Array[$$PROP_prototype][$$PROP_slice][$$PROP_call](arguments,1);args[$$PROP_unshift](this,selfObj);return goog[$$PROP_bind][$$PROP_apply](null,args)}else return goog[$$PROP_bind](this,selfObj)};Function[$$PROP_prototype].partial=function(){var args=GLOBAL_Array[$$PROP_prototype][$$PROP_slice][$$PROP_call](arguments);args[$$PROP_unshift](this,
null);return goog[$$PROP_bind][$$PROP_apply](null,args)};Function[$$PROP_prototype].inherits=function(parentCtor){goog.inherits(this,parentCtor)};Function[$$PROP_prototype].mixin=function(source){goog.mixin(this[$$PROP_prototype],source)}};goog.peoplesense={};goog.peoplesense.PeopleSenseContainer=function(){};goog.peoplesense.PeopleSenseContainer[$$PROP_prototype].openLightboxIframe=function(){};goog.peoplesense.PeopleSenseContainer[$$PROP_prototype].showMemberProfile=function(){};goog.peoplesense.PeopleSenseContainerImpl=null;goog.peoplesense.DirectPeopleSenseContainer=function(){goog.peoplesense.PeopleSenseContainer[$$PROP_call](this)};
goog.peoplesense.DirectPeopleSenseContainer[$$PROP_prototype].openLightboxIframe=function(url,st,opt_width,opt_height,opt_title,opt_additionalPostBodyParams,opt_siteid,opt_noDoubleIframe,opt_noIncludeFcAuth,opt_smallTitle,opt_callback){GLOBAL_window.google[$$PROP_friendconnect][$$PROP_container].openLightboxIframe(url,st,opt_width,opt_height,opt_title,opt_additionalPostBodyParams,opt_siteid,opt_noDoubleIframe,opt_noIncludeFcAuth,opt_smallTitle,opt_callback)};
goog.peoplesense.DirectPeopleSenseContainer[$$PROP_prototype].showMemberProfile=function(profileId,opt_siteId,opt_callback){GLOBAL_window.google[$$PROP_friendconnect][$$PROP_container].showMemberProfile(profileId,opt_siteId,opt_callback)};goog.peoplesense.PeopleSenseContainerImpl=new goog.peoplesense.DirectPeopleSenseContainer;goog.object={};goog[$$PROP_object].forEach=function(obj,f,opt_obj){for(var key in obj)f[$$PROP_call](opt_obj,obj[key],key,obj)};goog[$$PROP_object].filter=function(obj,f,opt_obj){var res={};for(var key in obj)if(f[$$PROP_call](opt_obj,obj[key],key,obj))res[key]=obj[key];return res};goog[$$PROP_object].map=function(obj,f,opt_obj){var res={};for(var key in obj)res[key]=f[$$PROP_call](opt_obj,obj[key],key,obj);return res};
goog[$$PROP_object].some=function(obj,f,opt_obj){for(var key in obj)if(f[$$PROP_call](opt_obj,obj[key],key,obj))return true;return false};goog[$$PROP_object].every=function(obj,f,opt_obj){for(var key in obj)if(!f[$$PROP_call](opt_obj,obj[key],key,obj))return false;return true};goog[$$PROP_object].getCount=function(obj){var rv=0;for(var key in obj)rv++;return rv};goog[$$PROP_object].getAnyKey=function(obj){for(var key in obj)return key};goog[$$PROP_object].getAnyValue=function(obj){for(var key in obj)return obj[key]};
goog[$$PROP_object].contains=function(obj,val){return goog[$$PROP_object].containsValue(obj,val)};goog[$$PROP_object].getValues=function(obj){var res=[],i=0;for(var key in obj)res[i++]=obj[key];return res};goog[$$PROP_object].getKeys=function(obj){var res=[],i=0;for(var key in obj)res[i++]=key;return res};goog[$$PROP_object].containsKey=function(obj,key){return key in obj};goog[$$PROP_object].containsValue=function(obj,val){for(var key in obj)if(obj[key]==val)return true;return false};
goog[$$PROP_object].findKey=function(obj,f,opt_this){for(var key in obj)if(f[$$PROP_call](opt_this,obj[key],key,obj))return key};goog[$$PROP_object].findValue=function(obj,f,opt_this){var key=goog[$$PROP_object].findKey(obj,f,opt_this);return key&&obj[key]};goog[$$PROP_object].isEmpty=function(obj){for(var key in obj)return false;return true};
goog[$$PROP_object].clear=function(obj){for(var keys=goog[$$PROP_object].getKeys(obj),i=keys[$$PROP_length]-1;i>=0;i--)goog[$$PROP_object][$$PROP_remove](obj,keys[i])};goog[$$PROP_object].remove=function(obj,key){var rv;if(rv=key in obj)delete obj[key];return rv};goog[$$PROP_object].add=function(obj,key,val){if(key in obj)throw GLOBAL_Error('The object already contains the key "'+key+'"');goog[$$PROP_object].set(obj,key,val)};
goog[$$PROP_object].get=function(obj,key,opt_val){if(key in obj)return obj[key];return opt_val};goog[$$PROP_object].set=function(obj,key,value){obj[key]=value};goog[$$PROP_object].setIfUndefined=function(obj,key,value){return key in obj?obj[key]:obj[key]=value};goog[$$PROP_object].clone=function(obj){var res={};for(var key in obj)res[key]=obj[key];return res};goog[$$PROP_object].transpose=function(obj){var transposed={};for(var key in obj)transposed[obj[key]]=key;return transposed};
goog[$$PROP_object].PROTOTYPE_FIELDS_=["constructor","hasOwnProperty","isPrototypeOf","propertyIsEnumerable","toLocaleString","toString","valueOf"];
goog[$$PROP_object].extend=function(target){for(var key,source,i=1;i<arguments[$$PROP_length];i++){source=arguments[i];for(key in source)target[key]=source[key];for(var j=0;j<goog[$$PROP_object].PROTOTYPE_FIELDS_[$$PROP_length];j++){key=goog[$$PROP_object].PROTOTYPE_FIELDS_[j];if(GLOBAL_Object[$$PROP_prototype][$$PROP_hasOwnProperty][$$PROP_call](source,key))target[key]=source[key]}}};
goog[$$PROP_object].create=function(){var argLength=arguments[$$PROP_length];if(argLength==1&&goog.isArray(arguments[0]))return goog[$$PROP_object].create[$$PROP_apply](null,arguments[0]);if(argLength%2)throw GLOBAL_Error("Uneven number of arguments");for(var rv={},i=0;i<argLength;i+=2)rv[arguments[i]]=arguments[i+1];return rv};
goog[$$PROP_object].createSet=function(){var argLength=arguments[$$PROP_length];if(argLength==1&&goog.isArray(arguments[0]))return goog[$$PROP_object].createSet[$$PROP_apply](null,arguments[0]);for(var rv={},i=0;i<argLength;i++)rv[arguments[i]]=true;return rv};var shindig=shindig||{};shindig.auth={getSecurityToken:function(){return GLOBAL_google[$$PROP_friendconnect][$$PROP_container].openSocialSecurityToken}};var opensocial=opensocial||{};opensocial.requestSendMessage=function(recipients,message,opt_callback,opt_params){opensocial.Container.get().requestSendMessage(recipients,message,opt_callback,opt_params)};opensocial.requestShareApp=function(recipients,reason,opt_callback,opt_params){opensocial.Container.get().requestShareApp(recipients,reason,opt_callback,opt_params)};
opensocial.requestCreateActivity=function(activity,priority,opt_callback){if(!activity||!activity.getField(opensocial.Activity.Field.TITLE)&&!activity.getField(opensocial.Activity.Field.TITLE_ID))opt_callback&&GLOBAL_window[$$PROP_setTimeout](function(){opt_callback(new opensocial.ResponseItem(null,null,opensocial.ResponseItem.Error.BAD_REQUEST,"You must pass in an activity with a title or title id."))},0);else opensocial.Container.get().requestCreateActivity(activity,priority,opt_callback)};
opensocial.CreateActivityPriority={HIGH:"HIGH",LOW:"LOW"};opensocial.hasPermission=function(permission){return opensocial.Container.get().hasPermission(permission)};opensocial.requestPermission=function(permissions,reason,opt_callback){opensocial.Container.get().requestPermission(permissions,reason,opt_callback)};opensocial.Permission={VIEWER:"viewer"};opensocial.getEnvironment=function(){return opensocial.Container.get().getEnvironment()};opensocial.newDataRequest=function(){return opensocial.Container.get().newDataRequest()};
opensocial.newActivity=function(params){return opensocial.Container.get().newActivity(params)};opensocial.newMediaItem=function(mimeType,url,opt_params){return opensocial.Container.get().newMediaItem(mimeType,url,opt_params)};opensocial.newMessage=function(body,opt_params){return opensocial.Container.get().newMessage(body,opt_params)};opensocial.EscapeType={HTML_ESCAPE:"htmlEscape",NONE:"none"};opensocial.newIdSpec=function(params){return opensocial.Container.get().newIdSpec(params)};
opensocial.newNavigationParameters=function(params){return opensocial.Container.get().newNavigationParameters(params)};Function[$$PROP_prototype].inherits=function(parentCtor){function tempCtor(){}SETPROP_prototype(tempCtor,parentCtor[$$PROP_prototype]);this.superClass_=parentCtor[$$PROP_prototype];SETPROP_prototype(this,new tempCtor);this[$$PROP_prototype].constructor=this};opensocial.Activity=function(params){this.fields_=params};
opensocial.Activity.Field={TITLE_ID:"titleId",TITLE:"title",TEMPLATE_PARAMS:"templateParams",URL:"url",MEDIA_ITEMS:"mediaItems",BODY_ID:"bodyId",BODY:"body",EXTERNAL_ID:"externalId",STREAM_TITLE:"streamTitle",STREAM_URL:"streamUrl",STREAM_SOURCE_URL:"streamSourceUrl",STREAM_FAVICON_URL:"streamFaviconUrl",PRIORITY:"priority",ID:"id",USER_ID:"userId",APP_ID:"appId",POSTED_TIME:"postedTime"};opensocial.Activity[$$PROP_prototype].getId=function(){return this.getField(opensocial.Activity.Field.ID)};
opensocial.Activity[$$PROP_prototype].getField=function(key,opt_params){return opensocial.Container.getField(this.fields_,key,opt_params)};opensocial.Activity[$$PROP_prototype].setField=function(key,data){return this.fields_[key]=data};opensocial.Address=function(opt_params){this.fields_=opt_params||{}};
opensocial.Address.Field={TYPE:"type",UNSTRUCTURED_ADDRESS:"unstructuredAddress",PO_BOX:"poBox",STREET_ADDRESS:"streetAddress",EXTENDED_ADDRESS:"extendedAddress",REGION:"region",LOCALITY:"locality",POSTAL_CODE:"postalCode",COUNTRY:"country",LATITUDE:"latitude",LONGITUDE:"longitude"};opensocial.Address[$$PROP_prototype].getField=function(key,opt_params){return opensocial.Container.getField(this.fields_,key,opt_params)};opensocial.BodyType=function(opt_params){this.fields_=opt_params||{}};
opensocial.BodyType.Field={BUILD:"build",HEIGHT:"height",WEIGHT:"weight",EYE_COLOR:"eyeColor",HAIR_COLOR:"hairColor"};opensocial.BodyType[$$PROP_prototype].getField=function(key,opt_params){return opensocial.Container.getField(this.fields_,key,opt_params)};opensocial.Collection=function(array,opt_offset,opt_totalSize){this.array_=array||[];this.offset_=opt_offset||0;this.totalSize_=opt_totalSize||this.array_[$$PROP_length]};a=opensocial.Collection[$$PROP_prototype];
a.getById=function(id){for(var i=0;i<this.size();i++){var item=this.array_[i];if(item.getId()==id)return item}return null};a.size=function(){return this.array_[$$PROP_length]};a.each=function(fn){for(var i=0;i<this.size();i++)fn(this.array_[i])};a.asArray=function(){return this.array_};a.getTotalSize=function(){return this.totalSize_};a.getOffset=function(){return this.offset_};opensocial.Container=function(){};opensocial.Container.container_=null;
opensocial.Container.setContainer=function(container){opensocial.Container.container_=container};opensocial.Container.get=function(){return opensocial.Container.container_};a=opensocial.Container[$$PROP_prototype];a.getEnvironment=function(){};a.requestSendMessage=function(recipients,message,opt_callback){opt_callback&&GLOBAL_window[$$PROP_setTimeout](function(){opt_callback(new opensocial.ResponseItem(null,null,opensocial.ResponseItem.Error.NOT_IMPLEMENTED,null))},0)};
a.requestShareApp=function(recipients,reason,opt_callback){opt_callback&&GLOBAL_window[$$PROP_setTimeout](function(){opt_callback(new opensocial.ResponseItem(null,null,opensocial.ResponseItem.Error.NOT_IMPLEMENTED,null))},0)};a.requestCreateActivity=function(activity,priority,opt_callback){opt_callback&&GLOBAL_window[$$PROP_setTimeout](function(){opt_callback(new opensocial.ResponseItem(null,null,opensocial.ResponseItem.Error.NOT_IMPLEMENTED,null))},0)};a.hasPermission=function(){return false};
a.requestPermission=function(permissions,reason,opt_callback){opt_callback&&GLOBAL_window[$$PROP_setTimeout](function(){opt_callback(new opensocial.ResponseItem(null,null,opensocial.ResponseItem.Error.NOT_IMPLEMENTED,null))},0)};a.requestData=function(){};a.newFetchPersonRequest=function(){};a.newFetchPeopleRequest=function(){};a.newFetchPersonAppDataRequest=function(){};a.newUpdatePersonAppDataRequest=function(){};a.newRemovePersonAppDataRequest=function(){};a.newFetchActivitiesRequest=function(){};
a.newCollection=function(array,opt_offset,opt_totalSize){return new opensocial.Collection(array,opt_offset,opt_totalSize)};a.newPerson=function(opt_params,opt_isOwner,opt_isViewer){return new opensocial.Person(opt_params,opt_isOwner,opt_isViewer)};a.newActivity=function(opt_params){return new opensocial.Activity(opt_params)};a.newMediaItem=function(mimeType,url,opt_params){return new opensocial.MediaItem(mimeType,url,opt_params)};
a.newMessage=function(body,opt_params){return new opensocial.Message(body,opt_params)};a.newIdSpec=function(params){return new opensocial.IdSpec(params)};a.newNavigationParameters=function(params){return new opensocial.NavigationParameters(params)};a.newResponseItem=function(originalDataRequest,data,opt_errorCode,opt_errorMessage){return new opensocial.ResponseItem(originalDataRequest,data,opt_errorCode,opt_errorMessage)};
a.newDataResponse=function(responseItems,opt_globalError){return new opensocial.DataResponse(responseItems,opt_globalError)};a.newDataRequest=function(){return new opensocial.DataRequest};a.newEnvironment=function(domain,supportedFields){return new opensocial.Environment(domain,supportedFields)};opensocial.Container.isArray=function(val){return val instanceof GLOBAL_Array};
opensocial.Container.getField=function(fields,key,opt_params){var value=fields[key];return opensocial.Container.escape(value,opt_params,false)};opensocial.Container.escape=function(value,opt_params,opt_escapeObjects){return opt_params&&opt_params.escapeType=="none"?value:GLOBAL_gadgets[$$PROP_util].escape(value,opt_escapeObjects)};opensocial.DataRequest=function(){this.requestObjects_=[]};opensocial.DataRequest[$$PROP_prototype].requestObjects_=null;
opensocial.DataRequest[$$PROP_prototype].getRequestObjects=function(){return this.requestObjects_};opensocial.DataRequest[$$PROP_prototype].add=function(request,opt_key){return this.requestObjects_[$$PROP_push]({key:opt_key,request:request})};opensocial.DataRequest[$$PROP_prototype].send=function(opt_callback){var callback=opt_callback||function(){};opensocial.Container.get().requestData(this,callback)};opensocial.DataRequest.SortOrder={TOP_FRIENDS:"topFriends",NAME:"name"};
opensocial.DataRequest.FilterType={ALL:"all",HAS_APP:"hasApp",TOP_FRIENDS:"topFriends",IS_FRIENDS_WITH:"isFriendsWith"};opensocial.DataRequest.PeopleRequestFields={PROFILE_DETAILS:"profileDetail",SORT_ORDER:"sortOrder",FILTER:"filter",FILTER_OPTIONS:"filterOptions",FIRST:"first",MAX:"max"};a=opensocial.DataRequest[$$PROP_prototype];a.addDefaultParam=function(params,name,defaultValue){params[name]=params[name]||defaultValue};
a.addDefaultProfileFields=function(params){var fields=opensocial.DataRequest.PeopleRequestFields,profileFields=params[fields.PROFILE_DETAILS]||[];params[fields.PROFILE_DETAILS]=profileFields.concat([opensocial.Person.Field.ID,opensocial.Person.Field.NAME,opensocial.Person.Field.THUMBNAIL_URL])};a.asArray=function(keys){return opensocial.Container.isArray(keys)?keys:[keys]};
a.newFetchPersonRequest=function(id,opt_params){opt_params=opt_params||{};this.addDefaultProfileFields(opt_params);return opensocial.Container.get().newFetchPersonRequest(id,opt_params)};
a.newFetchPeopleRequest=function(idSpec,opt_params){opt_params=opt_params||{};var fields=opensocial.DataRequest.PeopleRequestFields;this.addDefaultProfileFields(opt_params);this.addDefaultParam(opt_params,fields.SORT_ORDER,opensocial.DataRequest.SortOrder.TOP_FRIENDS);this.addDefaultParam(opt_params,fields.FILTER,opensocial.DataRequest.FilterType.ALL);this.addDefaultParam(opt_params,fields.FIRST,0);this.addDefaultParam(opt_params,fields.MAX,20);return opensocial.Container.get().newFetchPeopleRequest(idSpec,
opt_params)};opensocial.DataRequest.DataRequestFields={ESCAPE_TYPE:"escapeType"};opensocial.DataRequest[$$PROP_prototype].newFetchPersonAppDataRequest=function(idSpec,keys,opt_params){return opensocial.Container.get().newFetchPersonAppDataRequest(idSpec,this.asArray(keys),opt_params)};opensocial.DataRequest[$$PROP_prototype].newUpdatePersonAppDataRequest=function(id,key,value){return opensocial.Container.get().newUpdatePersonAppDataRequest(id,key,value)};
opensocial.DataRequest[$$PROP_prototype].newRemovePersonAppDataRequest=function(id,keys){return opensocial.Container.get().newRemovePersonAppDataRequest(id,keys)};opensocial.DataRequest.ActivityRequestFields={APP_ID:"appId"};opensocial.DataRequest[$$PROP_prototype].newFetchActivitiesRequest=function(idSpec,opt_params){opt_params=opt_params||{};return opensocial.Container.get().newFetchActivitiesRequest(idSpec,opt_params)};
opensocial.DataResponse=function(responseItems,opt_globalError,opt_errorMessage){this.responseItems_=responseItems;this.globalError_=opt_globalError;this.errorMessage_=opt_errorMessage};opensocial.DataResponse[$$PROP_prototype].hadError=function(){return!!this.globalError_};opensocial.DataResponse[$$PROP_prototype].getErrorMessage=function(){return this.errorMessage_};opensocial.DataResponse[$$PROP_prototype].get=function(key){return this.responseItems_[key]};
opensocial.Email=function(opt_params){this.fields_=opt_params||{}};opensocial.Email.Field={TYPE:"type",ADDRESS:"address"};opensocial.Email[$$PROP_prototype].getField=function(key,opt_params){return opensocial.Container.getField(this.fields_,key,opt_params)};opensocial.Enum=function(key,displayValue){this.key=key;this.displayValue=displayValue};opensocial.Enum[$$PROP_prototype].getKey=function(){return GLOBAL_gadgets[$$PROP_util].escape(this.key)};
opensocial.Enum[$$PROP_prototype].getDisplayValue=function(){return GLOBAL_gadgets[$$PROP_util].escape(this.displayValue)};opensocial.Enum.Smoker={NO:"NO",YES:"YES",SOCIALLY:"SOCIALLY",OCCASIONALLY:"OCCASIONALLY",REGULARLY:"REGULARLY",HEAVILY:"HEAVILY",QUITTING:"QUITTING",QUIT:"QUIT"};opensocial.Enum.Drinker={NO:"NO",YES:"YES",SOCIALLY:"SOCIALLY",OCCASIONALLY:"OCCASIONALLY",REGULARLY:"REGULARLY",HEAVILY:"HEAVILY",QUITTING:"QUITTING",QUIT:"QUIT"};opensocial.Enum.Gender={MALE:"MALE",FEMALE:"FEMALE"};
opensocial.Enum.LookingFor={DATING:"DATING",FRIENDS:"FRIENDS",RELATIONSHIP:"RELATIONSHIP",NETWORKING:"NETWORKING",ACTIVITY_PARTNERS:"ACTIVITY_PARTNERS",RANDOM:"RANDOM"};opensocial.Enum.Presence={AWAY:"AWAY",CHAT:"CHAT",DND:"DND",OFFLINE:"OFFLINE",ONLINE:"ONLINE",XA:"XA"};opensocial.Environment=function(domain,supportedFields){this.domain=domain;this.supportedFields=supportedFields};opensocial.Environment[$$PROP_prototype].getDomain=function(){return this.domain};
opensocial.Environment.ObjectType={PERSON:"person",ADDRESS:"address",BODY_TYPE:"bodyType",EMAIL:"email",NAME:"name",ORGANIZATION:"organization",PHONE:"phone",URL:"url",ACTIVITY:"activity",MEDIA_ITEM:"mediaItem",MESSAGE:"message",MESSAGE_TYPE:"messageType",SORT_ORDER:"sortOrder",FILTER_TYPE:"filterType"};opensocial.Environment[$$PROP_prototype].supportsField=function(objectType,fieldName){var supportedObjectFields=this.supportedFields[objectType]||[];return!!supportedObjectFields[fieldName]};
opensocial.IdSpec=function(opt_params){this.fields_=opt_params||{}};opensocial.IdSpec.Field={USER_ID:"userId",GROUP_ID:"groupId",NETWORK_DISTANCE:"networkDistance"};opensocial.IdSpec.PersonId={OWNER:"OWNER",VIEWER:"VIEWER"};opensocial.IdSpec.GroupId={SELF:"SELF",FRIENDS:"FRIENDS",ALL:"ALL"};opensocial.IdSpec[$$PROP_prototype].getField=function(key,opt_params){return opensocial.Container.getField(this.fields_,key,opt_params)};
opensocial.IdSpec[$$PROP_prototype].setField=function(key,data){return this.fields_[key]=data};opensocial.MediaItem=function(mimeType,url,opt_params){this.fields_=opt_params||{};this.fields_[opensocial.MediaItem.Field.MIME_TYPE]=mimeType;this.fields_[opensocial.MediaItem.Field.URL]=url};opensocial.MediaItem.Type={IMAGE:"image",VIDEO:"video",AUDIO:"audio"};opensocial.MediaItem.Field={TYPE:"type",MIME_TYPE:"mimeType",URL:"url"};
opensocial.MediaItem[$$PROP_prototype].getField=function(key,opt_params){return opensocial.Container.getField(this.fields_,key,opt_params)};opensocial.MediaItem[$$PROP_prototype].setField=function(key,data){return this.fields_[key]=data};opensocial.Message=function(body,opt_params){this.fields_=opt_params||{};this.fields_[opensocial.Message.Field.BODY]=body};opensocial.Message.Field={TYPE:"type",TITLE:"title",BODY:"body",TITLE_ID:"titleId",BODY_ID:"bodyId"};
opensocial.Message.Type={EMAIL:"email",NOTIFICATION:"notification",PRIVATE_MESSAGE:"privateMessage",PUBLIC_MESSAGE:"publicMessage"};opensocial.Message[$$PROP_prototype].getField=function(key,opt_params){return opensocial.Container.getField(this.fields_,key,opt_params)};opensocial.Message[$$PROP_prototype].setField=function(key,data){return this.fields_[key]=data};opensocial.Name=function(opt_params){this.fields_=opt_params||{}};
opensocial.Name.Field={FAMILY_NAME:"familyName",GIVEN_NAME:"givenName",ADDITIONAL_NAME:"additionalName",HONORIFIC_PREFIX:"honorificPrefix",HONORIFIC_SUFFIX:"honorificSuffix",UNSTRUCTURED:"unstructured"};opensocial.Name[$$PROP_prototype].getField=function(key,opt_params){return opensocial.Container.getField(this.fields_,key,opt_params)};opensocial.NavigationParameters=function(opt_params){this.fields_=opt_params||{}};opensocial.NavigationParameters.Field={VIEW:"view",OWNER:"owner",PARAMETERS:"parameters"};
opensocial.NavigationParameters[$$PROP_prototype].getField=function(key,opt_params){return opensocial.Container.getField(this.fields_,key,opt_params)};opensocial.NavigationParameters[$$PROP_prototype].setField=function(key,data){return this.fields_[key]=data};opensocial.NavigationParameters.DestinationType={VIEWER_DESTINATION:"viewerDestination",RECIPIENT_DESTINATION:"recipientDestination"};opensocial.Organization=function(opt_params){this.fields_=opt_params||{}};
opensocial.Organization.Field={NAME:"name",TITLE:"title",DESCRIPTION:"description",FIELD:"field",SUB_FIELD:"subField",START_DATE:"startDate",END_DATE:"endDate",SALARY:"salary",ADDRESS:"address",WEBPAGE:"webpage"};opensocial.Organization[$$PROP_prototype].getField=function(key,opt_params){return opensocial.Container.getField(this.fields_,key,opt_params)};opensocial.Person=function(opt_params,opt_isOwner,opt_isViewer){this.fields_=opt_params||{};this.isOwner_=opt_isOwner;this.isViewer_=opt_isViewer};
opensocial.Person.Field={ID:"id",NAME:"name",NICKNAME:"nickname",THUMBNAIL_URL:"thumbnailUrl",PROFILE_URL:"profileUrl",CURRENT_LOCATION:"currentLocation",ADDRESSES:"addresses",EMAILS:"emails",PHONE_NUMBERS:"phoneNumbers",ABOUT_ME:"aboutMe",STATUS:"status",PROFILE_SONG:"profileSong",PROFILE_VIDEO:"profileVideo",GENDER:"gender",SEXUAL_ORIENTATION:"sexualOrientation",RELATIONSHIP_STATUS:"relationshipStatus",AGE:"age",DATE_OF_BIRTH:"dateOfBirth",BODY_TYPE:"bodyType",ETHNICITY:"ethnicity",SMOKER:"smoker",
DRINKER:"drinker",CHILDREN:"children",PETS:"pets",LIVING_ARRANGEMENT:"livingArrangement",TIME_ZONE:"timeZone",LANGUAGES_SPOKEN:"languagesSpoken",JOBS:"jobs",JOB_INTERESTS:"jobInterests",SCHOOLS:"schools",INTERESTS:"interests",URLS:"urls",MUSIC:"music",MOVIES:"movies",TV_SHOWS:"tvShows",BOOKS:"books",ACTIVITIES:"activities",SPORTS:"sports",HEROES:"heroes",QUOTES:"quotes",CARS:"cars",FOOD:"food",TURN_ONS:"turnOns",TURN_OFFS:"turnOffs",TAGS:"tags",ROMANCE:"romance",SCARED_OF:"scaredOf",HAPPIEST_WHEN:"happiestWhen",
FASHION:"fashion",HUMOR:"humor",LOOKING_FOR:"lookingFor",RELIGION:"religion",POLITICAL_VIEWS:"politicalViews",HAS_APP:"hasApp",NETWORK_PRESENCE:"networkPresence"};opensocial.Person[$$PROP_prototype].getId=function(){return this.getField(opensocial.Person.Field.ID)};var ORDERED_NAME_FIELDS_=[opensocial.Name.Field.HONORIFIC_PREFIX,opensocial.Name.Field.GIVEN_NAME,opensocial.Name.Field.FAMILY_NAME,opensocial.Name.Field.HONORIFIC_SUFFIX,opensocial.Name.Field.ADDITIONAL_NAME];
opensocial.Person[$$PROP_prototype].getDisplayName=function(){var name=this.getField(opensocial.Person.Field.NAME);if(name){var unstructured=name.getField(opensocial.Name.Field.UNSTRUCTURED);if(unstructured)return unstructured;for(var fullName="",i=0;i<ORDERED_NAME_FIELDS_[$$PROP_length];i++){var nameValue=name.getField(ORDERED_NAME_FIELDS_[i]);if(nameValue)fullName+=nameValue+" "}return fullName[$$PROP_replace](/^\s+|\s+$/g,"")}return this.getField(opensocial.Person.Field.NICKNAME)};
opensocial.Person[$$PROP_prototype].getField=function(key,opt_params){return opensocial.Container.getField(this.fields_,key,opt_params)};opensocial.Person[$$PROP_prototype].isViewer=function(){return!!this.isViewer_};opensocial.Person[$$PROP_prototype].isOwner=function(){return!!this.isOwner_};opensocial.Phone=function(opt_params){this.fields_=opt_params||{}};opensocial.Phone.Field={TYPE:"type",NUMBER:"number"};
opensocial.Phone[$$PROP_prototype].getField=function(key,opt_params){return opensocial.Container.getField(this.fields_,key,opt_params)};opensocial.ResponseItem=function(originalDataRequest,data,opt_errorCode,opt_errorMessage){this.originalDataRequest_=originalDataRequest;this.data_=data;this.errorCode_=opt_errorCode;this.errorMessage_=opt_errorMessage};opensocial.ResponseItem[$$PROP_prototype].hadError=function(){return!!this.errorCode_};
opensocial.ResponseItem.Error={NOT_IMPLEMENTED:"notImplemented",UNAUTHORIZED:"unauthorized",FORBIDDEN:"forbidden",BAD_REQUEST:"badRequest",INTERNAL_ERROR:"internalError",LIMIT_EXCEEDED:"limitExceeded"};opensocial.ResponseItem[$$PROP_prototype].getErrorCode=function(){return this.errorCode_};opensocial.ResponseItem[$$PROP_prototype].getErrorMessage=function(){return this.errorMessage_};opensocial.ResponseItem[$$PROP_prototype].getOriginalDataRequest=function(){return this.originalDataRequest_};
opensocial.ResponseItem[$$PROP_prototype].getData=function(){return this.data_};opensocial.Url=function(opt_params){this.fields_=opt_params||{}};opensocial.Url.Field={TYPE:"type",LINK_TEXT:"linkText",ADDRESS:"address"};opensocial.Url[$$PROP_prototype].getField=function(key,opt_params){return opensocial.Container.getField(this.fields_,key,opt_params)};var FieldTranslations={};
FieldTranslations.translateServerPersonToJsPerson=function(serverJson){if(serverJson.emails)for(var i=0;i<serverJson.emails[$$PROP_length];i++)serverJson.emails[i].address=serverJson.emails[i][$$PROP_value];if(serverJson.phoneNumbers)for(var p=0;p<serverJson.phoneNumbers[$$PROP_length];p++)serverJson.phoneNumbers[p].number=serverJson.phoneNumbers[p][$$PROP_value];if(serverJson.birthday)serverJson.dateOfBirth=serverJson.birthday;if(serverJson.utcOffset)serverJson.timeZone=serverJson.utcOffset;if(serverJson.addresses)for(var j=
0;j<serverJson.addresses[$$PROP_length];j++)serverJson.addresses[j].unstructuredAddress=serverJson.addresses[j].formatted;if(serverJson.gender){var key=serverJson.gender=="male"?"MALE":serverJson.gender=="female"?"FEMALE":null;serverJson.gender={key:key,displayValue:serverJson.gender}}FieldTranslations.translateUrlJson(serverJson.profileSong);FieldTranslations.translateUrlJson(serverJson.profileVideo);if(serverJson.urls)for(var u=0;u<serverJson.urls[$$PROP_length];u++)FieldTranslations.translateUrlJson(serverJson.urls[u]);
FieldTranslations.translateEnumJson(serverJson.drinker);FieldTranslations.translateEnumJson(serverJson.lookingFor);FieldTranslations.translateEnumJson(serverJson.networkPresence);FieldTranslations.translateEnumJson(serverJson.smoker);if(serverJson.organizations){serverJson.jobs=[];serverJson.schools=[];for(var o=0;o<serverJson.organizations[$$PROP_length];o++){var org=serverJson.organizations[o];if(org[$$PROP_type]=="job")serverJson.jobs[$$PROP_push](org);else org[$$PROP_type]=="school"&&serverJson.schools[$$PROP_push](org)}}};
FieldTranslations.translateEnumJson=function(enumJson){if(enumJson)enumJson.key=enumJson[$$PROP_value]};FieldTranslations.translateUrlJson=function(urlJson){if(urlJson)urlJson.address=urlJson[$$PROP_value]};FieldTranslations.translateJsPersonFieldsToServerFields=function(fields){for(var i=0;i<fields[$$PROP_length];i++)if(fields[i]=="dateOfBirth")fields[i]="birthday";else if(fields[i]=="timeZone")fields[i]="utcOffset";fields[$$PROP_push]("id");fields[$$PROP_push]("displayName")};
var JsonActivity=function(opt_params,opt_skipConversions){opt_params=opt_params||{};opt_skipConversions||JsonActivity.constructArrayObject(opt_params,"mediaItems",JsonMediaItem);opensocial.Activity[$$PROP_call](this,opt_params)};JsonActivity.inherits(opensocial.Activity);
JsonActivity[$$PROP_prototype].toJsonObject=function(){for(var jsonObject=JsonActivity.copyFields(this.fields_),oldMediaItems=jsonObject.mediaItems||[],newMediaItems=[],i=0;i<oldMediaItems[$$PROP_length];i++)newMediaItems[i]=oldMediaItems[i].toJsonObject();jsonObject.mediaItems=newMediaItems;return jsonObject};var JsonMediaItem=function(opt_params){opensocial.MediaItem[$$PROP_call](this,opt_params.mimeType,opt_params.url,opt_params)};JsonMediaItem.inherits(opensocial.MediaItem);
JsonMediaItem[$$PROP_prototype].toJsonObject=function(){return JsonActivity.copyFields(this.fields_)};JsonActivity.constructArrayObject=function(map,fieldName,className){var fieldValue=map[fieldName];if(fieldValue)for(var i=0;i<fieldValue[$$PROP_length];i++)fieldValue[i]=new className(fieldValue[i])};JsonActivity.copyFields=function(oldObject){var newObject={};for(var field in oldObject)newObject[field]=oldObject[field];return newObject};
var JsonPerson=function(opt_params){opt_params=opt_params||{};JsonPerson.constructObject(opt_params,"bodyType",opensocial.BodyType);JsonPerson.constructObject(opt_params,"currentLocation",opensocial.Address);JsonPerson.constructObject(opt_params,"dateOfBirth",Date);JsonPerson.constructObject(opt_params,"name",opensocial.Name);JsonPerson.constructObject(opt_params,"profileSong",opensocial.Url);JsonPerson.constructObject(opt_params,"profileVideo",opensocial.Url);JsonPerson.constructArrayObject(opt_params,
"addresses",opensocial.Address);JsonPerson.constructArrayObject(opt_params,"emails",opensocial.Email);JsonPerson.constructArrayObject(opt_params,"jobs",opensocial.Organization);JsonPerson.constructArrayObject(opt_params,"phoneNumbers",opensocial.Phone);JsonPerson.constructArrayObject(opt_params,"schools",opensocial.Organization);JsonPerson.constructArrayObject(opt_params,"urls",opensocial.Url);JsonPerson.constructEnum(opt_params,"gender");JsonPerson.constructEnum(opt_params,"smoker");JsonPerson.constructEnum(opt_params,
"drinker");JsonPerson.constructEnum(opt_params,"networkPresence");JsonPerson.constructEnumArray(opt_params,"lookingFor");opensocial.Person[$$PROP_call](this,opt_params,opt_params.isOwner,opt_params.isViewer)};JsonPerson.inherits(opensocial.Person);JsonPerson.constructEnum=function(map,fieldName){var fieldValue=map[fieldName];if(fieldValue)map[fieldName]=new opensocial.Enum(fieldValue.key,fieldValue.displayValue)};
JsonPerson.constructEnumArray=function(map,fieldName){var fieldValue=map[fieldName];if(fieldValue)for(var i=0;i<fieldValue[$$PROP_length];i++)fieldValue[i]=new opensocial.Enum(fieldValue[i].key,fieldValue[i].displayValue)};JsonPerson.constructObject=function(map,fieldName,className){var fieldValue=map[fieldName];if(fieldValue)map[fieldName]=new className(fieldValue)};
JsonPerson.constructArrayObject=function(map,fieldName,className){var fieldValue=map[fieldName];if(fieldValue)for(var i=0;i<fieldValue[$$PROP_length];i++)fieldValue[i]=new className(fieldValue[i])};JsonPerson[$$PROP_prototype].getDisplayName=function(){return this.getField("displayName")};
JsonRpcContainer=function(baseUrl,domain,supportedFieldsArray){opensocial.Container[$$PROP_call](this);var supportedFieldsMap={};for(var objectType in supportedFieldsArray)if(supportedFieldsArray[$$PROP_hasOwnProperty](objectType)){supportedFieldsMap[objectType]={};for(var i=0;i<supportedFieldsArray[objectType][$$PROP_length];i++){var supportedField=supportedFieldsArray[objectType][i];supportedFieldsMap[objectType][supportedField]=true}}this.environment_=new opensocial.Environment(domain,supportedFieldsMap);
this.baseUrl_=baseUrl;this.securityToken_=shindig.auth.getSecurityToken()};JsonRpcContainer.inherits(opensocial.Container);JsonRpcContainer[$$PROP_prototype].getEnvironment=function(){return this.environment_};JsonRpcContainer[$$PROP_prototype].requestCreateActivity=function(activity,priority,opt_callback){opt_callback=opt_callback||function(){};var req=opensocial.newDataRequest(),viewer=new opensocial.IdSpec({userId:"VIEWER"});req.add(this.newCreateActivityRequest(viewer,activity),"key");req[$$PROP_send](function(response){opt_callback(response.get("key"))})};
JsonRpcContainer[$$PROP_prototype].requestData=function(dataRequest,callback){callback=callback||function(){};var requestObjects=dataRequest.getRequestObjects(),totalRequests=requestObjects[$$PROP_length];if(totalRequests==0)GLOBAL_window[$$PROP_setTimeout](function(){callback(new opensocial.DataResponse({},true))},0);else{for(var jsonBatchData=GLOBAL_Array(totalRequests),j=0;j<totalRequests;j++){var requestObject=requestObjects[j];jsonBatchData[j]=requestObject.request.rpc;if(requestObject.key)jsonBatchData[j].id=
requestObject.key}var sendResponse=function(result){if(result.errors[0])JsonRpcContainer.generateErrorResponse(result,requestObjects,callback);else{result=result[$$PROP_data];for(var globalError=false,responseMap={},i=0;i<result[$$PROP_length];i++)result[result[i].id]=result[i];for(var k=0;k<requestObjects[$$PROP_length];k++){var request=requestObjects[k],response=result[k];if(request.key&&response.id!=request.key)throw"Request key("+request.key+") and response id("+response.id+") do not match";var rawData=
response[$$PROP_data],error=response.error,errorMessage="";if(error)errorMessage=error.message;var processedData=request.request.processResponse(request.request,rawData,error,errorMessage);globalError=globalError||processedData.hadError();if(request.key)responseMap[request.key]=processedData}var dataResponse=new opensocial.DataResponse(responseMap,globalError);callback(dataResponse)}},makeRequestParams={CONTENT_TYPE:"JSON",METHOD:"POST",AUTHORIZATION:"SIGNED",POST_DATA:GLOBAL_gadgets.json.stringify(jsonBatchData)},
url=[this.baseUrl_,"/rpc"],token=shindig.auth.getSecurityToken();token&&url[$$PROP_push]("?st=",encodeURIComponent(token));this.sendRequest(url[$$PROP_join](""),sendResponse,makeRequestParams,"application/json")}};JsonRpcContainer[$$PROP_prototype].sendRequest=function(relativeUrl,callback,params,contentType){GLOBAL_gadgets.io.makeNonProxiedRequest(relativeUrl,callback,params,contentType)};
JsonRpcContainer.generateErrorResponse=function(result,requestObjects,callback){for(var globalErrorCode=JsonRpcContainer.translateHttpError(result.errors[0]||result[$$PROP_data].error)||opensocial.ResponseItem.Error.INTERNAL_ERROR,errorResponseMap={},i=0;i<requestObjects[$$PROP_length];i++)errorResponseMap[requestObjects[i].key]=new opensocial.ResponseItem(requestObjects[i].request,null,globalErrorCode);callback(new opensocial.DataResponse(errorResponseMap,true))};
JsonRpcContainer.translateHttpError=function(httpError){if(httpError=="Error 501")return opensocial.ResponseItem.Error.NOT_IMPLEMENTED;else if(httpError=="Error 401")return opensocial.ResponseItem.Error.UNAUTHORIZED;else if(httpError=="Error 403")return opensocial.ResponseItem.Error.FORBIDDEN;else if(httpError=="Error 400")return opensocial.ResponseItem.Error.BAD_REQUEST;else if(httpError=="Error 500")return opensocial.ResponseItem.Error.INTERNAL_ERROR;else if(httpError=="Error 404")return opensocial.ResponseItem.Error.BAD_REQUEST;
else if(httpError=="Error 417")return opensocial.ResponseItem.Error.LIMIT_EXCEEDED};a=JsonRpcContainer[$$PROP_prototype];a.makeIdSpec=function(id){return new opensocial.IdSpec({userId:id})};
a.translateIdSpec=function(newIdSpec){var userIds=newIdSpec.getField("userId"),groupId=newIdSpec.getField("groupId");opensocial.Container.isArray(userIds)||(userIds=[userIds]);for(var i=0;i<userIds[$$PROP_length];i++)if(userIds[i]=="OWNER")userIds[i]="@owner";else if(userIds[i]=="VIEWER")userIds[i]="@viewer";if(groupId=="FRIENDS")groupId="@friends";else if(groupId=="SELF"||!groupId)groupId="@self";return{userId:userIds,groupId:groupId}};
a.newFetchPersonRequest=function(id,opt_params){var peopleRequest=this.newFetchPeopleRequest(this.makeIdSpec(id),opt_params),me=this;return new JsonRpcRequestItem(peopleRequest.rpc,function(rawJson){return me.createPersonFromJson(rawJson)})};
a.newFetchPeopleRequest=function(idSpec,opt_params){var rpc={method:"people.get"};rpc.params=this.translateIdSpec(idSpec);if(opt_params.profileDetail){FieldTranslations.translateJsPersonFieldsToServerFields(opt_params.profileDetail);rpc.params.fields=opt_params.profileDetail}if(opt_params.first)rpc.params.startIndex=opt_params.first;if(opt_params.max)rpc.params.count=opt_params.max;if(opt_params.sortOrder)rpc.params.sortBy=opt_params.sortOrder;if(opt_params.filter)rpc.params.filterBy=opt_params.filter;
if(idSpec.getField("networkDistance"))rpc.params.networkDistance=idSpec.getField("networkDistance");var me=this;return new JsonRpcRequestItem(rpc,function(rawJson){var jsonPeople;jsonPeople=rawJson.list?rawJson.list:[rawJson];for(var people=[],i=0;i<jsonPeople[$$PROP_length];i++)people[$$PROP_push](me.createPersonFromJson(jsonPeople[i]));return new opensocial.Collection(people,rawJson.startIndex,rawJson.totalResults)})};
a.createPersonFromJson=function(serverJson){FieldTranslations.translateServerPersonToJsPerson(serverJson);return new JsonPerson(serverJson)};a.getFieldsList=function(keys){return this.hasNoKeys(keys)||this.isWildcardKey(keys[0])?[]:keys};a.hasNoKeys=function(keys){return!keys||keys[$$PROP_length]==0};a.isWildcardKey=function(key){return key=="*"};
a.newFetchPersonAppDataRequest=function(idSpec,keys,opt_params){var rpc={method:"appdata.get"};rpc.params=this.translateIdSpec(idSpec);rpc.params.appId="@app";rpc.params.fields=this.getFieldsList(keys);if(idSpec.getField("networkDistance"))rpc.params.networkDistance=idSpec.getField("networkDistance");return new JsonRpcRequestItem(rpc,function(appData){return opensocial.Container.escape(appData,opt_params,true)})};
a.newUpdatePersonAppDataRequest=function(id,key,value){var rpc={method:"appdata.update"};rpc.params=this.translateIdSpec(this.makeIdSpec(id));rpc.params.appId="@app";rpc.params.data={};rpc.params[$$PROP_data][key]=value;rpc.params.fields=key;return new JsonRpcRequestItem(rpc)};a.newRemovePersonAppDataRequest=function(id,keys){var rpc={method:"appdata.delete"};rpc.params=this.translateIdSpec(this.makeIdSpec(id));rpc.params.appId="@app";rpc.params.fields=this.getFieldsList(keys);return new JsonRpcRequestItem(rpc)};
a.newFetchActivitiesRequest=function(idSpec){var rpc={method:"activities.get"};rpc.params=this.translateIdSpec(idSpec);rpc.params.appId="@app";if(idSpec.getField("networkDistance"))rpc.params.networkDistance=idSpec.getField("networkDistance");return new JsonRpcRequestItem(rpc,function(rawJson){rawJson=rawJson.list;for(var activities=[],i=0;i<rawJson[$$PROP_length];i++)activities[$$PROP_push](new JsonActivity(rawJson[i]));return new opensocial.Collection(activities)})};
a.newActivity=function(opt_params){return new JsonActivity(opt_params,true)};a.newMediaItem=function(mimeType,url,opt_params){opt_params=opt_params||{};opt_params.mimeType=mimeType;opt_params.url=url;return new JsonMediaItem(opt_params)};
a.newCreateActivityRequest=function(idSpec,activity){var rpc={method:"activities.create"};rpc.params=this.translateIdSpec(idSpec);rpc.params.appId="@app";if(idSpec.getField("networkDistance"))rpc.params.networkDistance=idSpec.getField("networkDistance");rpc.params.activity=activity.toJsonObject();return new JsonRpcRequestItem(rpc)};
var JsonRpcRequestItem=function(rpc,opt_processData){this.rpc=rpc;this.processData=opt_processData||function(rawJson){return rawJson};this.processResponse=function(originalDataRequest,rawJson,error,errorMessage){var errorCode=error?JsonRpcContainer.translateHttpError("Error "+error.code):null;return new opensocial.ResponseItem(originalDataRequest,error?null:this.processData(rawJson),errorCode,errorMessage)}};JsonRpcContainer.txns={};JsonRpcContainer.doesBrowserNotSupportContainerToGadget=GLOBAL_google[$$PROP_friendconnect].userAgent.WEBKIT&&!(typeof GLOBAL_window.postMessage==="function"||typeof GLOBAL_document.postMessage==="function");
JsonRpcContainer[$$PROP_prototype].sendRequest=function(relativeUrl,callback,params){var txnId=String(Math.round(2147483647*Math.random()));JsonRpcContainer.txns[txnId]={};JsonRpcContainer.txns[txnId].callback=callback;JsonRpcContainer.txns[txnId].result=[];JsonRpcContainer.txns[txnId].done=false;JsonRpcContainer.txns[txnId].total=0;JsonRpcContainer.txns[txnId].POST_DATA=params.POST_DATA;JsonRpcContainer.doesBrowserNotSupportContainerToGadget?GLOBAL_google[$$PROP_friendconnect][$$PROP_container].invokeOpenSocialApiViaIframe(txnId):
GLOBAL_gadgets.rpc[$$PROP_call](GLOBAL_google[$$PROP_friendconnect][$$PROP_container].getOpenSocialApiIframeId(),"FetchOpenSocialData",null,{txn:txnId,POST_DATA:params.POST_DATA})};
JsonRpcContainer.checkIfTxnDoneAndCallCallback_=function(txn){var response=JsonRpcContainer.txns[txn],reallyDone=false;if(response.done){reallyDone=true;for(var i=0;i<response.total;i++)if(typeof response.result[i]=="undefined"){reallyDone=false;break}if(reallyDone){var combinedString=response.result[$$PROP_join](""),result=GLOBAL_gadgets.json.parse(combinedString);response.callback(result);JsonRpcContainer.doesBrowserNotSupportContainerToGadget&&GLOBAL_google[$$PROP_friendconnect][$$PROP_container].removeOpenSocialApiViaIframe(txn);
delete response}}};JsonRpcContainer.processResponseRequest=function(data){var response=JsonRpcContainer.txns[data.txn];if(data.done){response.done=true;response.total=data.total}else response.result[data.index]=data.chunk;JsonRpcContainer.checkIfTxnDoneAndCallCallback_(data.txn)};GLOBAL_gadgets.rpc.register("FetchOpenSocialDataResponse",JsonRpcContainer.processResponseRequest);
JsonRpcContainer.fetchQueuedCommand=function(txnId){var txn=JsonRpcContainer.txns[txnId],txnData=!txn||{txn:txnId,POST_DATA:txn.POST_DATA};return txnData};GLOBAL_gadgets.rpc.register("FetchQueuedCommand",JsonRpcContainer.fetchQueuedCommand);
var supportedFields={name:["unstructured","familyName","givenName","additionalName","honorificPrefix","honorificSuffix"],person:["id","name","thumbnailUrl","nickname","profileUrl"],activity:["id","externalId","userId","appId","streamTitle","streamUrl","streamSourceUrl","streamFaviconUrl","title","body","url","mediaItems","postedTime"],activityMediaItem:["type","mimeType","url"],sortOrder:["name"],filterType:["all","hasApp"]};
opensocial.Container.setContainer(new JsonRpcContainer("friendconnect.google.com","friendconnect.google.com",supportedFields));opensocial.xmlutil=opensocial.xmlutil||{};opensocial.xmlutil.parser_=null;
opensocial.xmlutil.parseXML=function(str){if(typeof DOMParser!="undefined"){opensocial.xmlutil.parser_=opensocial.xmlutil.parser_||new DOMParser;var doc=opensocial.xmlutil.parser_.parseFromString(str,"text/xml");if(doc[$$PROP_firstChild]&&doc[$$PROP_firstChild][$$PROP_tagName]=="parsererror")throw doc[$$PROP_firstChild][$$PROP_firstChild][$$PROP_nodeValue];return doc}else if(typeof ActiveXObject!="undefined"){doc=new ActiveXObject("MSXML2.DomDocument");doc.validateOnParse=false;doc.loadXML(str);if(doc.parseError&&
doc.parseError.errorCode)throw doc.parseError.reason;return doc}throw"No XML parser found in this browser.";};opensocial.xmlutil.NSMAP={os:"http://opensocial.org/"};
opensocial.xmlutil.getRequiredNamespaces=function(xml){var codeToInject=[];for(var ns in opensocial.xmlutil.NSMAP)if(xml[$$PROP_indexOf]("<"+ns+":")>=0&&xml[$$PROP_indexOf]("xmlns:"+ns+":")<0){codeToInject[$$PROP_push](" xmlns:");codeToInject[$$PROP_push](ns);codeToInject[$$PROP_push]('="');codeToInject[$$PROP_push](opensocial.xmlutil.NSMAP[ns]);codeToInject[$$PROP_push]('"')}return codeToInject[$$PROP_join]("")};opensocial.xmlutil.ENTITIES='<!ENTITY nbsp "&#160;">';
opensocial.xmlutil.prepareXML=function(xml){var namespaces=opensocial.xmlutil.getRequiredNamespaces(xml);return"<!DOCTYPE root ["+opensocial.xmlutil.ENTITIES+']><root xml:space="preserve"'+namespaces+">"+xml+"</root>"};opensocial.data=opensocial[$$PROP_data]||{};
opensocial[$$PROP_data].DataContext=function(){var listeners=[],dataSets={},putDataSet=function(key,obj,opt_fireListeners){if(typeof obj==="undefined"||obj===null)delete dataSets[key];else dataSets[key]=obj;opt_fireListeners!==false&&fireCallbacks(key)},registerListener=function(keys,callback,oneTimeListener){var oneTime=!!oneTimeListener,listener={keys:{},callback:callback,oneTime:oneTime};if(typeof keys==="string"){listener.keys[keys]=true;if(keys!="*")keys=[keys]}else for(var i=0;i<keys[$$PROP_length];i++)listener.keys[keys[i]]=
true;listeners[$$PROP_push](listener);keys!=="*"&&isDataReady(listener.keys)&&GLOBAL_window[$$PROP_setTimeout](function(){var listener$$0=listener,key=keys;if(isDataReady(listener$$0.keys)){listener$$0.callback(key);listener$$0.oneTime&&removeListener(listener$$0)}},1)},isDataReady=function(keys){if(keys["*"])return true;for(var key in keys)if(typeof dataSets[key]==="undefined")return false;return true},removeListener=function(listener){for(var i=0;i<listeners[$$PROP_length];++i)if(listeners[i]==
listener){listeners.splice(i,1);return}},fireCallbacks=function(keys){if(typeof keys=="string")keys=[keys];for(var i=0;i<listeners[$$PROP_length];++i)for(var listener=listeners[i],j=0;j<keys[$$PROP_length];j++){var key=keys[j];if(listener.keys[key]||listener.keys["*"]){var listener$$0=listener,key$$0=keys;if(isDataReady(listener$$0.keys)){listener$$0.callback(key$$0);listener$$0.oneTime&&removeListener(listener$$0)}break}}};return{getData:function(){return dataSets},registerListener:function(keys,
callback){registerListener(keys,callback,false)},registerOneTimeListener_:function(keys,callback){registerListener(keys,callback,true)},getDataSet:function(key){return dataSets[key]},putDataSet:function(key,obj){putDataSet(key,obj,true)},putDataSets:function(dataSets){var keys=[];for(var key in dataSets){keys[$$PROP_push](key);putDataSet(key,dataSets[key],false)}fireCallbacks(keys)}}}();opensocial[$$PROP_data].getDataContext=function(){return opensocial[$$PROP_data].DataContext};
opensocial[$$PROP_data].ATTR_KEY="key";opensocial[$$PROP_data].SCRIPT_TYPE="text/os-data";opensocial[$$PROP_data].NSMAP={};opensocial[$$PROP_data].VAR_REGEX=/^([\w\W]*?)(\$\{[^\}]*\})([\w\W]*)$/;
opensocial[$$PROP_data].RequestDescriptor=function(xmlNode){this.tagName=xmlNode[$$PROP_tagName];this.tagParts=this[$$PROP_tagName][$$PROP_split](":");this.attributes={};this.dependencies=false;for(var i=0;i<xmlNode[$$PROP_attributes][$$PROP_length];++i){var name=xmlNode[$$PROP_attributes][i].nodeName;if(name){var value=xmlNode[$$PROP_getAttribute](name);if(name&&value){this[$$PROP_attributes][name]=value;this.computeNeededKeys_(value)}}}this.key=this[$$PROP_attributes][opensocial[$$PROP_data].ATTR_KEY];
this.register_()};opensocial[$$PROP_data].RequestDescriptor[$$PROP_prototype].hasAttribute=function(name){return!!this[$$PROP_attributes][name]};opensocial[$$PROP_data].RequestDescriptor[$$PROP_prototype].getAttribute=function(name){var attrExpression=this[$$PROP_attributes][name];if(!attrExpression)return attrExpression;var expression=opensocial[$$PROP_data].parseExpression_(attrExpression);if(!expression)return attrExpression;return opensocial[$$PROP_data].DataContext.evalExpression(expression)};
opensocial[$$PROP_data].parseExpression_=function(value){if(!value[$$PROP_length])return null;var substRex=opensocial[$$PROP_data].VAR_REGEX,text=value,parts=[],match=text[$$PROP_match](substRex);if(!match)return null;for(;match;){match[1][$$PROP_length]>0&&parts[$$PROP_push](opensocial[$$PROP_data].transformLiteral_(match[1]));var expr=match[2][$$PROP_substring](2,match[2][$$PROP_length]-1);parts[$$PROP_push]("("+expr+")");text=match[3];match=text[$$PROP_match](substRex)}text[$$PROP_length]>0&&parts[$$PROP_push](opensocial[$$PROP_data].transformLiteral_(text));
return parts[$$PROP_join]("+")};opensocial[$$PROP_data].transformLiteral_=function(string){return"'"+string[$$PROP_replace](/'/g,"\\'")[$$PROP_replace](/\n/g," ")+"'"};opensocial[$$PROP_data].RequestDescriptor[$$PROP_prototype].sendRequest=function(){var ns=opensocial[$$PROP_data].NSMAP[this.tagParts[0]],handler=null;if(ns)handler=ns[this.tagParts[1]];if(!handler)throw"Data handler undefined for "+this[$$PROP_tagName];handler(this)};
opensocial[$$PROP_data].RequestDescriptor[$$PROP_prototype].getSendRequestClosure=function(){var self=this;return function(){self.sendRequest()}};
opensocial[$$PROP_data].RequestDescriptor[$$PROP_prototype].computeNeededKeys_=function(attribute){for(var substRex=opensocial[$$PROP_data].VAR_REGEX,match=attribute[$$PROP_match](substRex);match;){var token=match[2][$$PROP_substring](2,match[2][$$PROP_length]-1),key=token[$$PROP_split](".")[0];if(!this.neededKeys)this.neededKeys={};this.neededKeys[key]=true;match=match[3][$$PROP_match](substRex)}};opensocial[$$PROP_data].RequestDescriptor[$$PROP_prototype].register_=function(){opensocial[$$PROP_data].registerRequestDescriptor(this)};
opensocial[$$PROP_data].DataContext.evalExpression=function(expr){return(new Function("context","with (context) return "+expr))(opensocial[$$PROP_data].DataContext[$$PROP_getData]())};opensocial[$$PROP_data].requests_={};opensocial[$$PROP_data].registerRequestDescriptor=function(requestDescriptor){if(opensocial[$$PROP_data].requests_[requestDescriptor.key])throw"Request already registered for "+requestDescriptor.key;opensocial[$$PROP_data].requests_[requestDescriptor.key]=requestDescriptor};
opensocial[$$PROP_data].currentAPIRequest_=null;opensocial[$$PROP_data].currentAPIRequestKeys_=null;opensocial[$$PROP_data].currentAPIRequestCallbacks_=null;opensocial[$$PROP_data].getCurrentAPIRequest=function(){if(!opensocial[$$PROP_data].currentAPIRequest_){opensocial[$$PROP_data].currentAPIRequest_=opensocial.newDataRequest();opensocial[$$PROP_data].currentAPIRequestKeys_=[];opensocial[$$PROP_data].currentAPIRequestCallbacks_={}}return opensocial[$$PROP_data].currentAPIRequest_};
opensocial[$$PROP_data].addToCurrentAPIRequest=function(request,key,opt_callback){opensocial[$$PROP_data].getCurrentAPIRequest().add(request,key);opensocial[$$PROP_data].currentAPIRequestKeys_[$$PROP_push](key);if(opt_callback)opensocial[$$PROP_data].currentAPIRequestCallbacks_[key]=opt_callback;GLOBAL_window[$$PROP_setTimeout](opensocial[$$PROP_data].sendCurrentAPIRequest_,0)};
opensocial[$$PROP_data].sendCurrentAPIRequest_=function(){if(opensocial[$$PROP_data].currentAPIRequest_){opensocial[$$PROP_data].currentAPIRequest_[$$PROP_send](opensocial[$$PROP_data].createSharedRequestCallback_());opensocial[$$PROP_data].currentAPIRequest_=null}};
opensocial[$$PROP_data].createSharedRequestCallback_=function(){var keys=opensocial[$$PROP_data].currentAPIRequestKeys_,callbacks=opensocial[$$PROP_data].currentAPIRequestCallbacks_;return function(data){opensocial[$$PROP_data].onAPIResponse(data,keys,callbacks)}};
opensocial[$$PROP_data].onAPIResponse=function(responseItem,keys,callbacks){for(var i=0;i<keys[$$PROP_length];i++){var key=keys[i],item=responseItem.get(key),data=null;item.hadError()||(data=opensocial[$$PROP_data].extractJson_(item,key));callbacks[key]?callbacks[key](key,data):opensocial[$$PROP_data].DataContext.putDataSet(key,data)}};
opensocial[$$PROP_data].extractJson_=function(responseItem,key){var data=responseItem[$$PROP_getData]();if(data.array_){for(var out=[],i=0;i<data.array_[$$PROP_length];i++)out[$$PROP_push](data.array_[i].fields_);data=out;var request=opensocial[$$PROP_data].requests_[key];if(request[$$PROP_tagName]=="os:PeopleRequest"){var groupId=request[$$PROP_getAttribute]("groupId");if((!groupId||groupId=="@self")&&data[$$PROP_length]==1)data=data[0]}}else data=data.fields_||data;return data};
opensocial[$$PROP_data].registerRequestHandler=function(name,handler){var tagParts=name[$$PROP_split](":"),ns=opensocial[$$PROP_data].NSMAP[tagParts[0]];if(ns){if(ns[tagParts[1]])throw"Request handler "+tagParts[1]+" is already defined.";}else{opensocial.xmlutil.NSMAP[tagParts[0]]||(opensocial.xmlutil.NSMAP[tagParts[0]]=null);ns=opensocial[$$PROP_data].NSMAP[tagParts[0]]={}}ns[tagParts[1]]=handler};
opensocial[$$PROP_data].processDocumentMarkup=function(opt_doc){for(var doc=opt_doc||GLOBAL_document,nodes=doc[$$PROP_getElementsByTagName]("script"),i=0;i<nodes[$$PROP_length];++i){var node=nodes[i];node[$$PROP_type]==opensocial[$$PROP_data].SCRIPT_TYPE&&opensocial[$$PROP_data].loadRequests(node)}opensocial[$$PROP_data].registerRequestDependencies();opensocial[$$PROP_data].executeRequests()};GLOBAL_window.gadgets&&GLOBAL_window.gadgets[$$PROP_util]&&GLOBAL_gadgets[$$PROP_util].registerOnLoadHandler(opensocial[$$PROP_data].processDocumentMarkup);
opensocial[$$PROP_data].loadRequests=function(xml){if(typeof xml!="string"){var node=xml;xml=node[$$PROP_value]||node.innerHTML}opensocial[$$PROP_data].loadRequestsFromMarkup_(xml)};opensocial[$$PROP_data].loadRequestsFromMarkup_=function(xml){xml=opensocial.xmlutil.prepareXML(xml);for(var doc=opensocial.xmlutil.parseXML(xml),node=doc[$$PROP_firstChild];node[$$PROP_nodeType]!=1;)node=node[$$PROP_nextSibling];opensocial[$$PROP_data].processDataNode_(node)};
opensocial[$$PROP_data].processDataNode_=function(node){for(var child=node[$$PROP_firstChild];child;child=child[$$PROP_nextSibling])child[$$PROP_nodeType]==1&&new opensocial[$$PROP_data].RequestDescriptor(child)};
opensocial[$$PROP_data].registerRequestDependencies=function(){for(var key in opensocial[$$PROP_data].requests_){var request=opensocial[$$PROP_data].requests_[key],neededKeys=request.neededKeys,dependencies=[];for(var neededKey in neededKeys)opensocial[$$PROP_data].DataContext.getDataSet(neededKey)==null&&opensocial[$$PROP_data].requests_[neededKey]&&dependencies[$$PROP_push](neededKey);if(dependencies[$$PROP_length]>0){opensocial[$$PROP_data].DataContext.registerListener(dependencies,request.getSendRequestClosure());
request.dependencies=true}}};opensocial[$$PROP_data].executeRequests=function(){for(var key in opensocial[$$PROP_data].requests_){var request=opensocial[$$PROP_data].requests_[key];request.dependencies||request.sendRequest()}};opensocial[$$PROP_data].transformSpecialValue=function(value){if(value[$$PROP_substring](0,1)=="@")return value[$$PROP_substring](1).toUpperCase();return value};
opensocial[$$PROP_data].registerRequestHandler("os:ViewerRequest",function(descriptor){var req=opensocial[$$PROP_data].getCurrentAPIRequest().newFetchPersonRequest("VIEWER");opensocial[$$PROP_data].addToCurrentAPIRequest(req,descriptor.key)});opensocial[$$PROP_data].registerRequestHandler("os:OwnerRequest",function(descriptor){var req=opensocial[$$PROP_data].getCurrentAPIRequest().newFetchPersonRequest("OWNER");opensocial[$$PROP_data].addToCurrentAPIRequest(req,descriptor.key)});
opensocial[$$PROP_data].registerRequestHandler("os:PeopleRequest",function(descriptor){var userId=descriptor[$$PROP_getAttribute]("userId"),groupId=descriptor[$$PROP_getAttribute]("groupId")||"@self",idSpec={};idSpec.userId=opensocial[$$PROP_data].transformSpecialValue(userId);if(groupId!="@self")idSpec.groupId=opensocial[$$PROP_data].transformSpecialValue(groupId);var req=opensocial[$$PROP_data].getCurrentAPIRequest().newFetchPeopleRequest(opensocial.newIdSpec(idSpec));opensocial[$$PROP_data].addToCurrentAPIRequest(req,
descriptor.key)});
opensocial[$$PROP_data].registerRequestHandler("os:ActivitiesRequest",function(descriptor){var userId=descriptor[$$PROP_getAttribute]("userId"),groupId=descriptor[$$PROP_getAttribute]("groupId")||"@self",idSpec={};idSpec.userId=opensocial[$$PROP_data].transformSpecialValue(userId);if(groupId!="@self")idSpec.groupId=opensocial[$$PROP_data].transformSpecialValue(groupId);var req=opensocial[$$PROP_data].getCurrentAPIRequest().newFetchActivitiesRequest(opensocial.newIdSpec(idSpec));opensocial[$$PROP_data].addToCurrentAPIRequest(req,
descriptor.key)});
opensocial[$$PROP_data].registerRequestHandler("os:HttpRequest",function(descriptor){var href=descriptor[$$PROP_getAttribute]("href"),format=descriptor[$$PROP_getAttribute]("format")||"json",params={};params[GLOBAL_gadgets.io.RequestParameters.CONTENT_TYPE]=format[$$PROP_toLowerCase]()=="text"?GLOBAL_gadgets.io.ContentType.TEXT:GLOBAL_gadgets.io.ContentType.JSON;params[GLOBAL_gadgets.io.RequestParameters.METHOD]=GLOBAL_gadgets.io.MethodType.GET;GLOBAL_gadgets.io.makeRequest(href,function(obj){opensocial[$$PROP_data].DataContext.putDataSet(descriptor.key,
obj[$$PROP_data])},params)});(opensocial[$$PROP_data].populateParams_=function(){GLOBAL_window.gadgets&&GLOBAL_gadgets[$$PROP_util].hasFeature("views")&&opensocial[$$PROP_data].DataContext.putDataSet("ViewParams",GLOBAL_gadgets.views.getParams())})();var TYPE_boolean="boolean",TYPE_number="number",TYPE_object="object",TYPE_undefined="undefined";function arraySlice(){return Function[$$PROP_prototype][$$PROP_call][$$PROP_apply](GLOBAL_Array[$$PROP_prototype][$$PROP_slice],arguments)}
function bindFully(object,method){var args=arraySlice(arguments,2);return function(){return method[$$PROP_apply](object,args)}}function domTraverseElements(node,callback){var traverser=new DomTraverser(callback);traverser.run(node)}function DomTraverser(callback){this.callback_=callback}DomTraverser[$$PROP_prototype].run=function(root){var me=this;for(me.queue_=[root];me.queue_[$$PROP_length];)me.process_(me.queue_.shift())};
DomTraverser[$$PROP_prototype].process_=function(node){var me=this;me.callback_(node);for(var c=node[$$PROP_firstChild];c;c=c[$$PROP_nextSibling])c[$$PROP_nodeType]==1&&me.queue_[$$PROP_push](c)};function displayDefault(node){node.style.display=""}function displayNone(node){node.style.display="none"}var VAR_index="Index",VAR_count="Count",VAR_this="$this",VAR_context="$context",VAR_top="$top",VAR_loop="$loop",GLOB_default="$default",CHAR_colon=":",REGEXP_semicolon=/\s*;\s*/;
function JsEvalContext(){this.constructor_[$$PROP_apply](this,arguments)}
JsEvalContext[$$PROP_prototype].constructor_=function(opt_data,opt_parent){var me=this;if(!me.vars_)me.vars_={};if(opt_parent){var to=me.vars_,from=opt_parent.vars_;for(var p in from)to[p]=from[p]}else{var to$$0=me.vars_,from$$0=JsEvalContext.globals_;for(var p$$0 in from$$0)to$$0[p$$0]=from$$0[p$$0]}me.vars_[VAR_this]=opt_data;me.vars_[VAR_context]=me;me.data_=typeof opt_data!=TYPE_undefined&&opt_data!=null?opt_data:"";if(!opt_parent)me.vars_[VAR_top]=me.data_};JsEvalContext.globals_={};
JsEvalContext.setGlobal=function(name,value){JsEvalContext.globals_[name]=value};JsEvalContext.setGlobal(GLOB_default,null);JsEvalContext.recycledInstances_=[];JsEvalContext.create=function(opt_data,opt_parent){if(JsEvalContext.recycledInstances_[$$PROP_length]>0){var instance=JsEvalContext.recycledInstances_.pop();JsEvalContext[$$PROP_call](instance,opt_data,opt_parent);return instance}else return new JsEvalContext(opt_data,opt_parent)};
JsEvalContext.recycle=function(instance){for(var i in instance.vars_)delete instance.vars_[i];instance.data_=null;JsEvalContext.recycledInstances_[$$PROP_push](instance)};a=JsEvalContext[$$PROP_prototype];a.jsexec=function(exprFunction,template){try{return exprFunction[$$PROP_call](template,this.vars_,this.data_)}catch(e){return JsEvalContext.globals_[GLOB_default]}};
a.clone=function(data,index,count){var ret=JsEvalContext.create(data,this);if(typeof index=="number"||typeof count=="number"){var loopContext={};loopContext[VAR_index]=index;loopContext[VAR_count]=count;ret.setVariable(VAR_loop,loopContext)}return ret};a.setVariable=function(name,value){this.vars_[name]=value};a.getVariable=function(name){return this.vars_[name]};a.evalExpression=function(expr,opt_template){var exprFunction=jsEvalToFunction(expr);return this.jsexec(exprFunction,opt_template)};
var STRING_a="a_",STRING_b="b_",STRING_with="with (a_) with (b_) return ";JsEvalContext.evalToFunctionCache_={};function jsEvalToFunction(expr){if(!JsEvalContext.evalToFunctionCache_[expr])try{JsEvalContext.evalToFunctionCache_[expr]=new Function(STRING_a,STRING_b,STRING_with+expr)}catch(e){}return JsEvalContext.evalToFunctionCache_[expr]}function jsEvalToSelf(expr){return expr}
function jsEvalToValues(expr){for(var ret=[],values=expr[$$PROP_split](REGEXP_semicolon),i=0,I=values[$$PROP_length];i<I;++i){var colon=values[i][$$PROP_indexOf](CHAR_colon);if(!(colon<0)){var label,str=values[i][$$PROP_substr](0,colon),str$$0=str[$$PROP_replace](/^\s+/,"");label=str$$0[$$PROP_replace](/\s+$/,"");var value=jsEvalToFunction(values[i][$$PROP_substr](colon+1));ret[$$PROP_push](label,value)}}return ret}
function jsEvalToExpressions(expr){for(var ret=[],values=expr[$$PROP_split](REGEXP_semicolon),i=0,I=values[$$PROP_length];i<I;++i)if(values[i]){var value=jsEvalToFunction(values[i]);ret[$$PROP_push](value)}return ret}
var ATT_select="jsselect",ATT_instance="jsinstance",ATT_display="jsdisplay",ATT_values="jsvalues",ATT_vars="jsvars",ATT_eval="jseval",ATT_transclude="transclude",ATT_content="jscontent",ATT_skip="jsskip",ATT_innerselect="jsinnerselect",ATT_jstcache="jstcache",PROP_jstcache="__jstcache",CHAR_asterisk="*",CHAR_dollar="$",CHAR_period=".",CHAR_ampersand="&",STRING_id="id",STRING_asteriskzero="*0",STRING_zero="0";
function jstProcess(context,template){var processor=new JstProcessor;JstProcessor.prepareTemplate_(template);var JSCompiler_temp_const=processor,JSCompiler_inline_result;JSCompiler_inline_result=template?template[$$PROP_nodeType]==9?template:template.ownerDocument||GLOBAL_document:GLOBAL_document;JSCompiler_temp_const.document_=JSCompiler_inline_result;processor.run_(bindFully(processor,processor.jstProcessOuter_,context,template))}function JstProcessor(){}JstProcessor.jstid_=0;
JstProcessor.jstcache_={};JstProcessor.jstcache_[0]={};JstProcessor.jstcacheattributes_={};JstProcessor.attributeValues_={};JstProcessor.attributeList_=[];JstProcessor.prepareTemplate_=function(template){template[PROP_jstcache]||domTraverseElements(template,function(node){JstProcessor.prepareNode_(node)})};
var JST_ATTRIBUTES=[[ATT_select,jsEvalToFunction],[ATT_display,jsEvalToFunction],[ATT_values,jsEvalToValues],[ATT_vars,jsEvalToValues],[ATT_eval,jsEvalToExpressions],[ATT_transclude,jsEvalToSelf],[ATT_content,jsEvalToFunction],[ATT_skip,jsEvalToFunction],[ATT_innerselect,jsEvalToFunction]];
JstProcessor.prepareNode_=function(node){if(node[PROP_jstcache])return node[PROP_jstcache];var jstid=node[$$PROP_getAttribute](ATT_jstcache);if(jstid!=null)return node[PROP_jstcache]=JstProcessor.jstcache_[jstid];var attributeValues=JstProcessor.attributeValues_,attributeList=JstProcessor.attributeList_;SETPROP_length(attributeList,0);var i,I;i=0;for(I=JST_ATTRIBUTES[$$PROP_length];i<I;++i){var name=JST_ATTRIBUTES[i][0],value=node[$$PROP_getAttribute](name);attributeValues[name]=value;value!=null&&
attributeList[$$PROP_push](name+"="+value)}if(attributeList[$$PROP_length]==0){node[$$PROP_setAttribute](ATT_jstcache,STRING_zero);return node[PROP_jstcache]=JstProcessor.jstcache_[0]}var attstring=attributeList[$$PROP_join](CHAR_ampersand);if(jstid=JstProcessor.jstcacheattributes_[attstring]){node[$$PROP_setAttribute](ATT_jstcache,jstid);return node[PROP_jstcache]=JstProcessor.jstcache_[jstid]}var jstcache={};i=0;for(I=JST_ATTRIBUTES[$$PROP_length];i<I;++i){var att=JST_ATTRIBUTES[i];name=att[0];
var parse=att[1];value=attributeValues[name];if(value!=null)jstcache[name]=parse(value)}jstid=""+ ++JstProcessor.jstid_;node[$$PROP_setAttribute](ATT_jstcache,jstid);JstProcessor.jstcache_[jstid]=jstcache;JstProcessor.jstcacheattributes_[attstring]=jstid;return node[PROP_jstcache]=jstcache};a=JstProcessor[$$PROP_prototype];
a.run_=function(f){var me=this,calls=me.calls_=[],queueIndices=me.queueIndices_=[];me.arrayPool_=[];f();for(var queue,queueIndex,method,arg1,arg2;calls[$$PROP_length];){queue=calls[calls[$$PROP_length]-1];queueIndex=queueIndices[queueIndices[$$PROP_length]-1];if(queueIndex>=queue[$$PROP_length]){me.recycleArray_(calls.pop());queueIndices.pop()}else{method=queue[queueIndex++];arg1=queue[queueIndex++];arg2=queue[queueIndex++];queueIndices[queueIndices[$$PROP_length]-1]=queueIndex;method[$$PROP_call](me,
arg1,arg2)}}};a.push_=function(args){this.calls_[$$PROP_push](args);this.queueIndices_[$$PROP_push](0)};a.setDebugging=function(){};a.createArray_=function(){return this.arrayPool_[$$PROP_length]?this.arrayPool_.pop():[]};a.recycleArray_=function(array){SETPROP_length(array,0);this.arrayPool_[$$PROP_push](array)};
a.jstProcessOuter_=function(context,template){var me=this,jstAttributes=me.jstAttributes_(template),transclude=jstAttributes[ATT_transclude];if(transclude){var tr;var name=transclude,doc=GLOBAL_document,section;if(section=doc[$$PROP_getElementById](name)){JstProcessor.prepareTemplate_(section);var ret=section[$$PROP_cloneNode](true);ret[$$PROP_removeAttribute](STRING_id);tr=ret}else tr=null;if(tr){template[$$PROP_parentNode].replaceChild(tr,template);var call=me.createArray_();call[$$PROP_push](me.jstProcessOuter_,
context,tr);me.push_(call)}else template[$$PROP_parentNode][$$PROP_removeChild](template)}else{var select=jstAttributes[ATT_select];select?me.jstSelect_(context,template,select):me.jstProcessInner_(context,template)}};
a.jstProcessInner_=function(context,template){var me=this,jstAttributes=me.jstAttributes_(template),display=jstAttributes[ATT_display];if(display){var shouldDisplay=context.jsexec(display,template);if(!shouldDisplay){displayNone(template);return}displayDefault(template)}var values=jstAttributes[ATT_vars];values&&me.jstVars_(context,template,values);(values=jstAttributes[ATT_values])&&me.jstValues_(context,template,values);var expressions=jstAttributes[ATT_eval];if(expressions)for(var i=0,I=expressions[$$PROP_length];i<
I;++i)context.jsexec(expressions[i],template);var skip=jstAttributes[ATT_skip];if(skip){var shouldSkip=context.jsexec(skip,template);if(shouldSkip)return}var content=jstAttributes[ATT_content];if(content)me.jstContent_(context,template,content);else{for(var queue=me.createArray_(),ctx=null,c=template[$$PROP_firstChild];c;c=c[$$PROP_nextSibling])if(c[$$PROP_nodeType]==1){if(!ctx){ctx=context;var selectInner=jstAttributes[ATT_innerselect];if(selectInner&&selectInner!=VAR_this)ctx=context.clone(context.jsexec(selectInner,
template),0,0)}queue[$$PROP_push](me.jstProcessOuter_,ctx,c)}queue[$$PROP_length]&&me.push_(queue)}};
a.jstSelect_=function(context,template,select){var me=this,value=context.jsexec(select,template),instance=template[$$PROP_getAttribute](ATT_instance),instanceLast=false;if(instance)if(instance[$$PROP_charAt](0)==CHAR_asterisk){var s=instance[$$PROP_substr](1);instance=parseInt(s,10);instanceLast=true}else instance=parseInt(instance,10);var multiple=value!=null&&typeof value==TYPE_object&&typeof value[$$PROP_length]==TYPE_number,count=multiple?value[$$PROP_length]:1,multipleEmpty=multiple&&count==
0;if(multiple)if(multipleEmpty)if(instance)template[$$PROP_parentNode][$$PROP_removeChild](template);else{template[$$PROP_setAttribute](ATT_instance,STRING_asteriskzero);displayNone(template)}else{displayDefault(template);if(instance===null||instance===""||instanceLast&&instance<count-1){var queue=me.createArray_(),instancesStart=instance||0,i,I,clone;i=instancesStart;for(I=count-1;i<I;++i){var node=template[$$PROP_cloneNode](true);template[$$PROP_parentNode].insertBefore(node,template);jstSetInstance(node,
value,i);clone=context.clone(value[i],i,count);queue[$$PROP_push](me.jstProcessInner_,clone,node,JsEvalContext.recycle,clone,null)}jstSetInstance(template,value,i);clone=context.clone(value[i],i,count);queue[$$PROP_push](me.jstProcessInner_,clone,template,JsEvalContext.recycle,clone,null);me.push_(queue)}else if(instance<count){var v=value[instance];jstSetInstance(template,value,instance);clone=context.clone(v,instance,count);queue=me.createArray_();queue[$$PROP_push](me.jstProcessInner_,clone,template,
JsEvalContext.recycle,clone,null);me.push_(queue)}else template[$$PROP_parentNode][$$PROP_removeChild](template)}else if(value==null)displayNone(template);else{displayDefault(template);clone=context.clone(value,0,1);queue=me.createArray_();queue[$$PROP_push](me.jstProcessInner_,clone,template,JsEvalContext.recycle,clone,null);me.push_(queue)}};
a.jstVars_=function(context,template,values){for(var i=0,I=values[$$PROP_length];i<I;i+=2){var label=values[i],value=context.jsexec(values[i+1],template);context.setVariable(label,value)}};
a.jstValues_=function(context,template,values){for(var i=0,I=values[$$PROP_length];i<I;i+=2){var label=values[i],value=context.jsexec(values[i+1],template);if(label[$$PROP_charAt](0)==CHAR_dollar)context.setVariable(label,value);else if(label[$$PROP_charAt](0)==CHAR_period){for(var nameSpaceLabel=label[$$PROP_substr](1)[$$PROP_split](CHAR_period),nameSpaceObject=template,nameSpaceDepth=nameSpaceLabel[$$PROP_length],j=0,J=nameSpaceDepth-1;j<J;++j){var jLabel=nameSpaceLabel[j];nameSpaceObject[jLabel]||
(nameSpaceObject[jLabel]={});nameSpaceObject=nameSpaceObject[jLabel]}nameSpaceObject[nameSpaceLabel[nameSpaceDepth-1]]=value}else if(label)if(typeof value==TYPE_boolean)value?template[$$PROP_setAttribute](label,label):template[$$PROP_removeAttribute](label);else template[$$PROP_setAttribute](label,""+value)}};
a.jstContent_=function(context,template,content){var value=""+context.jsexec(content,template);if(template.innerHTML!=value){for(;template[$$PROP_firstChild];)template[$$PROP_firstChild][$$PROP_parentNode][$$PROP_removeChild](template[$$PROP_firstChild]);var t=this.document_[$$PROP_createTextNode](value);template[$$PROP_appendChild](t)}};
a.jstAttributes_=function(template){if(template[PROP_jstcache])return template[PROP_jstcache];var jstid=template[$$PROP_getAttribute](ATT_jstcache);if(jstid)return template[PROP_jstcache]=JstProcessor.jstcache_[jstid];return JstProcessor.prepareNode_(template)};function jstSetInstance(template,values,index){index==values[$$PROP_length]-1?template[$$PROP_setAttribute](ATT_instance,CHAR_asterisk+index):template[$$PROP_setAttribute](ATT_instance,""+index)}a.logState_=function(){};a.getLogs=function(){return this.logs_};
opensocial=opensocial||{};opensocial.template=opensocial.template||{};var os=opensocial.template;os.log=function(msg){var console=GLOBAL_window.console;console&&console.log&&console.log(msg)};GLOBAL_window.log=os.log;os.warn=function(msg){os.log("WARNING: "+msg)};os.isArray=function(obj){return typeof obj=="object"&&typeof obj[$$PROP_length]=="number"&&typeof obj[$$PROP_push]=="function"};os.ATT_customtag="customtag";os.VAR_my="$my";os.VAR_cur="$cur";os.VAR_node="$node";os.VAR_msg="Msg";
os.VAR_parentnode="$parentnode";os.VAR_uniqueId="$uniqueId";os.VAR_identifierresolver="$_ir";os.VAR_callbacks="$callbacks_";os.regExps_={ONLY_WHITESPACE:/^[ \t\n]*$/,VARIABLE_SUBSTITUTION:/^([\w\W]*?)(\$\{[^\}]*\})([\w\W]*)$/};
os.compileTemplate=function(node,opt_id){if(typeof node=="string")return os.compileTemplateString(node,opt_id);opt_id=opt_id||node.name;var src=node[$$PROP_value]||node.innerHTML;src=os.trim(src);var template=os.compileTemplateString(src,opt_id);if(!node.name)node.name=template.id;return template};os.compileTemplateString=function(src,opt_id){src=opensocial.xmlutil.prepareXML(src);var doc=opensocial.xmlutil.parseXML(src);return os.compileXMLDoc(doc,opt_id)};
os.renderTemplateNode_=function(compiledNode,context){var template=compiledNode[$$PROP_cloneNode](true);template[$$PROP_removeAttribute]&&template[$$PROP_removeAttribute](STRING_id);jstProcess(context,template);return template};os.elementIdCounter_=0;
os.createTemplateCustomTag=function(template){return function(node,data,context){context.setVariable(os.VAR_my,node);context.setVariable(os.VAR_node,node);context.setVariable(os.VAR_uniqueId,os.elementIdCounter_++);var ret=template.render(data,context);os.markNodeToSkip(ret);return ret}};
os.computeChildMap_=function(node){for(var map={},i=0;i<node[$$PROP_childNodes][$$PROP_length];i++){var child=node[$$PROP_childNodes][i];if(child[$$PROP_tagName]){var name=child[$$PROP_getAttribute](os.ATT_customtag);if(name){var parts=name[$$PROP_split](":");parts[$$PROP_length]==2?name=parts[1]:name=parts[0]}else name=child[$$PROP_tagName];name=name[$$PROP_toLowerCase]();var prev=map[name];if(prev)if(os.isArray(prev))prev[$$PROP_push](child);else{map[name]=[];map[name][$$PROP_push](prev);map[name][$$PROP_push](child)}else map[name]=
child}}return map};os.createNodeAccessor_=function(node){return function(name){return os.getValueFromNode_(node,name)}};os.gadgetPrefs_=null;if(GLOBAL_window.gadgets&&GLOBAL_window.gadgets.Prefs)os.gadgetPrefs_=new GLOBAL_window.gadgets.Prefs;os.getPrefMessage=function(key){if(!os.gadgetPrefs_)return null;return os.gadgetPrefs_.getMsg(key)};os.globalDisallowedAttributes_={data:1};os.customAttributes_={};os.registerAttribute_=function(attrName,functor){os.customAttributes_[attrName]=functor};
os.doAttribute=function(node,attrName,data,context){if(os.customAttributes_[$$PROP_hasOwnProperty](attrName)){var attrFunctor=os.customAttributes_[attrName];attrFunctor(node,node[$$PROP_getAttribute](attrName),data,context)}};
os.doTag=function(node,ns,tag,data,context){var tagFunction=os.getCustomTag(ns,tag);if(tagFunction){for(var ctx=null,child=node[$$PROP_firstChild];child;child=child[$$PROP_nextSibling])if(child[$$PROP_nodeType]==1){if(ctx==null){var selectInner=node[PROP_jstcache]?node[PROP_jstcache][ATT_innerselect]:null;if(selectInner){var contextData=context.jsexec(selectInner,node);ctx=context.clone(contextData,0,0)}else ctx=context}jstProcess(ctx,child);os.markNodeToSkip(child)}ctx=context.clone({},0,0);var result=
tagFunction[$$PROP_call](null,node,data,ctx);if(!result&&typeof result!="string")throw"Custom tag <"+ns+":"+tag+"> failed to return anything.";if(typeof result=="string")node.innerHTML=result?result:"";else if(os.isArray(result)){os.removeChildren(node);for(var i=0;i<result[$$PROP_length];i++)if(result[i][$$PROP_nodeType]==1||result[i][$$PROP_nodeType]==3){node[$$PROP_appendChild](result[i]);result[i][$$PROP_nodeType]==1&&os.markNodeToSkip(result[i])}}else{var callbacks=context.getVariable(os.VAR_callbacks),
resultNode=null;if(result[$$PROP_nodeType]==1)resultNode=result;else if(result.root&&result.root[$$PROP_nodeType]==1)resultNode=result.root;if(resultNode&&resultNode!=node&&(!resultNode[$$PROP_parentNode]||resultNode[$$PROP_parentNode][$$PROP_nodeType]==11)){os.removeChildren(node);node[$$PROP_appendChild](resultNode);os.markNodeToSkip(resultNode)}result.onAttach&&callbacks[$$PROP_push](result)}}else os.warn("Custom tag <"+ns+":"+tag+"> not defined.")};
os.setContextNode_=function(data,context){data[$$PROP_nodeType]==1&&context.setVariable(os.VAR_node,data)};os.markNodeToSkip=function(node){node[$$PROP_setAttribute](ATT_skip,"true");node[$$PROP_removeAttribute](ATT_select);node[$$PROP_removeAttribute](ATT_eval);node[$$PROP_removeAttribute](ATT_values);node[$$PROP_removeAttribute](ATT_display);node[PROP_jstcache]=null;node[$$PROP_removeAttribute](ATT_jstcache)};os.nsmap_={};
os.createNamespace=function(ns,url){var tags=os.nsmap_[ns];if(os.nsmap_[$$PROP_hasOwnProperty](ns))if(opensocial.xmlutil.NSMAP[ns]==null)opensocial.xmlutil.NSMAP[ns]=url;else{if(opensocial.xmlutil.NSMAP[ns]!=url)throw"Namespace "+ns+" already defined with url "+opensocial.xmlutil.NSMAP[ns];}else{tags={};os.nsmap_[ns]=tags;opensocial.xmlutil.NSMAP[ns]=url}return tags};os.getNamespace=function(prefix){return os.nsmap_[prefix]};
os.addNamespace=function(ns,url,nsObj){if(!os.nsmap_[$$PROP_hasOwnProperty](ns))if(opensocial.xmlutil.NSMAP[ns]==null){opensocial.xmlutil.NSMAP[ns]=url;return}else throw"Namespace '"+ns+"' already exists!";os.nsmap_[ns]=nsObj;opensocial.xmlutil.NSMAP[ns]=url};os.getCustomTag=function(ns,tag){if(!os.nsmap_[$$PROP_hasOwnProperty](ns))return null;var nsObj=os.nsmap_[ns];return nsObj.getTag?nsObj.getTag(tag):nsObj[tag]};os.trim=function(string){return string[$$PROP_replace](/^\s+|\s+$/g,"")};
os.isAlphaNum=function(ch){return ch>="a"&&ch<="z"||ch>="A"&&ch<="Z"||ch>="0"&&ch<="9"||ch=="_"};os.removeChildren=function(node){for(;node[$$PROP_firstChild];)node[$$PROP_removeChild](node[$$PROP_firstChild])};os.appendChildren=function(sourceNode,targetNode){if(sourceNode!=targetNode)for(;sourceNode[$$PROP_firstChild];)targetNode[$$PROP_appendChild](sourceNode[$$PROP_firstChild])};
os.getPropertyGetterName=function(propertyName){var getter="get"+propertyName[$$PROP_charAt](0).toUpperCase()+propertyName[$$PROP_substring](1);return getter};
os.convertToCamelCase=function(str){var upper=str.toUpperCase(),words=str[$$PROP_toLowerCase]()[$$PROP_split]("_"),out=[],index=words[0][$$PROP_length]+1;out[$$PROP_push](words[0]);for(var i=1;i<words[$$PROP_length];++i){if(words[i][$$PROP_length]>0){var piece=upper[$$PROP_charAt](index)+words[i][$$PROP_substring](1);out[$$PROP_push](piece)}index+=words[i][$$PROP_length]+1}return out[$$PROP_join]("")};
os.createContext=function(data,opt_globals){var context=JsEvalContext.create(data);context.setVariable(os.VAR_callbacks,[]);context.setVariable(os.VAR_identifierresolver,os.getFromContext);if(opt_globals)for(var global in opt_globals)opt_globals.hasOwnproperty(global)&&context.setVariable(global,opt_globals[global]);return context};os.Template=function(opt_id){this.templateRoot_=GLOBAL_document[$$PROP_createElement]("span");this.id=opt_id||"template_"+os.Template.idCounter_++};
os.Template.idCounter_=0;os.registeredTemplates_={};os.registerTemplate=function(template){os.registeredTemplates_[template.id]=template};os.unRegisterTemplate=function(template){delete os.registeredTemplates_[template.id]};os.getTemplate=function(templateId){return os.registeredTemplates_[templateId]};os.Template[$$PROP_prototype].setCompiledNode_=function(node){os.removeChildren(this.templateRoot_);this.templateRoot_[$$PROP_appendChild](node)};
os.Template[$$PROP_prototype].setCompiledNodes_=function(nodes){os.removeChildren(this.templateRoot_);for(var i=0;i<nodes[$$PROP_length];i++)this.templateRoot_[$$PROP_appendChild](nodes[i])};os.Template[$$PROP_prototype].render=function(opt_data,opt_context){opt_context||(opt_context=os.createContext(opt_data));return os.renderTemplateNode_(this.templateRoot_,opt_context)};
os.Template[$$PROP_prototype].renderInto=function(root,opt_data,opt_context){opt_context||(opt_context=os.createContext(opt_data));var result=this.render(opt_data,opt_context);os.removeChildren(root);os.appendChildren(result,root);os.fireCallbacks(opt_context)};os.SEMICOLON=";";os.isIe=GLOBAL_navigator.userAgent[$$PROP_indexOf]("Opera")!=0&&GLOBAL_navigator.userAgent[$$PROP_indexOf]("MSIE")!=-1;
os.compileXMLNode=function(node,opt_id){for(var nodes=[],child=node[$$PROP_firstChild];child;child=child[$$PROP_nextSibling])if(child[$$PROP_nodeType]==1)nodes[$$PROP_push](os.compileNode_(child));else if(child[$$PROP_nodeType]==3)if(child!=node[$$PROP_firstChild]||!child[$$PROP_nodeValue][$$PROP_match](os.regExps_.ONLY_WHITESPACE))for(var compiled=os.breakTextNode_(child),i=0;i<compiled[$$PROP_length];i++)nodes[$$PROP_push](compiled[i]);var template=new os.Template(opt_id);template.setCompiledNodes_(nodes);
return template};os.compileXMLDoc=function(doc,opt_id){for(var node=doc[$$PROP_firstChild];node[$$PROP_nodeType]!=1;)node=node[$$PROP_nextSibling];return os.compileXMLNode(node,opt_id)};os.operatorMap={and:"&&",eq:"==",lte:"<=",lt:"<",gte:">=",gt:">",neq:"!=",or:"||",not:"!"};os.regExps_.SPLIT_INTO_TOKENS=/"(?:[^"\\]|\\.)*"|'(?:[^'\\]|\\.)*'|\w+|[^"'\w]+/g;
os.remapOperators_=function(src){return src[$$PROP_replace](os.regExps_.SPLIT_INTO_TOKENS,function(token){return os.operatorMap[$$PROP_hasOwnProperty](token)?os.operatorMap[token]:token})};os.transformVariables_=function(expr){return expr=os.replaceTopLevelVars_(expr)};os.variableMap_={my:os.VAR_my,My:os.VAR_my,cur:VAR_this,Cur:VAR_this,$cur:VAR_this,Top:VAR_top,Context:VAR_loop};
os.replaceTopLevelVars_=function(text){var regex;regex=os.regExps_.TOP_LEVEL_VAR_REPLACEMENT;if(!regex){regex=/(^|[^.$a-zA-Z0-9])([$a-zA-Z0-9]+)/g;os.regExps_.TOP_LEVEL_VAR_REPLACEMENT=regex}return text[$$PROP_replace](regex,function(whole,left,right){return os.variableMap_[$$PROP_hasOwnProperty](right)?left+os.variableMap_[right]:whole})};os.identifierResolver_=function(data,name){return data[$$PROP_hasOwnProperty](name)?data[name]:"get"in data?data.get(name):null};
os.setIdentifierResolver=function(resolver){os.identifierResolver_=resolver};
os.getFromContext=function(context,name,opt_default){var ret;if(context.vars_&&context.data_){if(context.data_[$$PROP_nodeType]==1){ret=os.getValueFromNode_(context.data_,name);if(ret==null)ret=void 0}else ret=os.identifierResolver_(context.data_,name);if(typeof ret=="undefined")ret=os.identifierResolver_(context.vars_,name);if(typeof ret=="undefined"&&context.vars_[os.VAR_my])ret=os.getValueFromNode_(context.vars_[os.VAR_my],name);if(typeof ret=="undefined"&&context.vars_[VAR_top])ret=context.vars_[VAR_top][name]}else ret=
context[$$PROP_nodeType]==1?os.getValueFromNode_(context,name):os.identifierResolver_(context,name);if(typeof ret=="undefined"||ret==null)ret=typeof opt_default!="undefined"?opt_default:"";return ret};os.transformExpression_=function(expr,opt_default){expr=os.remapOperators_(expr);expr=os.transformVariables_(expr);if(os.identifierResolver_)expr=os.wrapIdentifiersInExpression(expr,opt_default);return expr};os.attributeMap_={"if":ATT_display,repeat:ATT_select,cur:ATT_innerselect};
os.appendJSTAttribute_=function(node,attrName,value){var previousValue=node[$$PROP_getAttribute](attrName);if(previousValue)value=previousValue+";"+value;node[$$PROP_setAttribute](attrName,value)};
os.copyAttributes_=function(from,to,opt_customTag){for(var dynamicAttributes=null,i=0;i<from[$$PROP_attributes][$$PROP_length];i++){var name=from[$$PROP_attributes][i].nodeName,value=from[$$PROP_getAttribute](name);if(name&&value)if(name=="var")os.appendJSTAttribute_(to,ATT_vars,from[$$PROP_getAttribute](name)+": $this");else if(name=="context")os.appendJSTAttribute_(to,ATT_vars,from[$$PROP_getAttribute](name)+": "+VAR_loop);else if(name[$$PROP_length]<7||name[$$PROP_substring](0,6)!="xmlns:"){if(os.customAttributes_[name])os.appendJSTAttribute_(to,
ATT_eval,"os.doAttribute(this, '"+name+"', $this, $context)");else name=="repeat"&&os.appendJSTAttribute_(to,ATT_eval,"os.setContextNode_($this, $context)");var outName=os.attributeMap_[$$PROP_hasOwnProperty](name)?os.attributeMap_[name]:name,substitution=os.attributeMap_[name]||opt_customTag&&os.globalDisallowedAttributes_[outName]?null:os.parseAttribute_(value);if(substitution){if(outName=="class")outName=".className";else if(outName=="style")outName=".style.cssText";else if(to[$$PROP_getAttribute](os.ATT_customtag))outName=
"."+outName;else if(os.isIe&&!os.customAttributes_[outName]&&outName[$$PROP_substring](0,2)[$$PROP_toLowerCase]()=="on"){outName="."+outName;substitution="new Function("+substitution+")"}else if(outName=="selected"&&to[$$PROP_tagName]=="OPTION")outName=".selected";dynamicAttributes||(dynamicAttributes=[]);dynamicAttributes[$$PROP_push](outName+":"+substitution)}else{if(os.attributeMap_[$$PROP_hasOwnProperty](name)){if(value[$$PROP_length]>3&&value[$$PROP_substring](0,2)=="${"&&value[$$PROP_charAt](value[$$PROP_length]-
1)=="}")value=value[$$PROP_substring](2,value[$$PROP_length]-1);value=os.transformExpression_(value,"null")}else if(outName=="class")to[$$PROP_setAttribute]("className",value);else if(outName=="style")to.style.cssText=value;os.isIe&&!os.customAttributes_[$$PROP_hasOwnProperty](outName)&&outName[$$PROP_substring](0,2)[$$PROP_toLowerCase]()=="on"?to.attachEvent(outName,new Function(value)):to[$$PROP_setAttribute](outName,value)}}}dynamicAttributes&&os.appendJSTAttribute_(to,ATT_values,dynamicAttributes[$$PROP_join](";"))};
os.compileNode_=function(node){if(node[$$PROP_nodeType]==3){var textNode=node[$$PROP_cloneNode](false);return os.breakTextNode_(textNode)}else if(node[$$PROP_nodeType]==1){var output;if(node[$$PROP_tagName][$$PROP_indexOf](":")>0)if(node[$$PROP_tagName]=="os:Repeat"){output=GLOBAL_document[$$PROP_createElement](os.computeContainerTag_(node));output[$$PROP_setAttribute](ATT_select,os.parseAttribute_(node[$$PROP_getAttribute]("expression")));var varAttr=node[$$PROP_getAttribute]("var");varAttr&&os.appendJSTAttribute_(output,
ATT_vars,varAttr+": $this");var contextAttr=node[$$PROP_getAttribute]("context");contextAttr&&os.appendJSTAttribute_(output,ATT_vars,contextAttr+": "+VAR_loop);os.appendJSTAttribute_(output,ATT_eval,"os.setContextNode_($this, $context)")}else if(node[$$PROP_tagName]=="os:If"){output=GLOBAL_document[$$PROP_createElement](os.computeContainerTag_(node));output[$$PROP_setAttribute](ATT_display,os.parseAttribute_(node[$$PROP_getAttribute]("condition")))}else{output=GLOBAL_document[$$PROP_createElement]("span");
output[$$PROP_setAttribute](os.ATT_customtag,node[$$PROP_tagName]);var custom=node[$$PROP_tagName][$$PROP_split](":");os.appendJSTAttribute_(output,ATT_eval,'os.doTag(this, "'+custom[0]+'", "'+custom[1]+'", $this, $context)');var context=node[$$PROP_getAttribute]("cur")||"{}";output[$$PROP_setAttribute](ATT_innerselect,context);if(node[$$PROP_tagName]=="os:render"||node[$$PROP_tagName]=="os:Render"||node[$$PROP_tagName]=="os:renderAll"||node[$$PROP_tagName]=="os:RenderAll")os.appendJSTAttribute_(output,
ATT_values,os.VAR_parentnode+":"+os.VAR_node);os.copyAttributes_(node,output,node[$$PROP_tagName])}else output=os.xmlToHtml_(node);if(output&&!os.processTextContent_(node,output))for(var child=node[$$PROP_firstChild];child;child=child[$$PROP_nextSibling]){var compiledChild=os.compileNode_(child);if(compiledChild)if(os.isArray(compiledChild))for(var i=0;i<compiledChild[$$PROP_length];i++)output[$$PROP_appendChild](compiledChild[i]);else if(compiledChild[$$PROP_tagName]=="TR"&&output[$$PROP_tagName]==
"TABLE"){for(var lastEl=output.lastChild;lastEl&&lastEl[$$PROP_nodeType]!=1&&lastEl.previousSibling;)lastEl=lastEl.previousSibling;if(!lastEl||lastEl[$$PROP_tagName]!="TBODY"){lastEl=GLOBAL_document[$$PROP_createElement]("tbody");output[$$PROP_appendChild](lastEl)}lastEl[$$PROP_appendChild](compiledChild)}else output[$$PROP_appendChild](compiledChild)}return output}return null};
os.computeContainerTag_=function(element){var child=element[$$PROP_firstChild];if(child){for(;child&&!child[$$PROP_tagName];)child=child[$$PROP_nextSibling];if(child){var tag=child[$$PROP_tagName][$$PROP_toLowerCase]();if(tag=="option")return"optgroup";if(tag=="tr")return"tbody"}}return"span"};os.ENTITIES='<!ENTITY nbsp "&#160;">';os.xmlToHtml_=function(xmlNode){var htmlNode=GLOBAL_document[$$PROP_createElement](xmlNode[$$PROP_tagName]);os.copyAttributes_(xmlNode,htmlNode);return htmlNode};
os.fireCallbacks=function(context){for(var callbacks=context.getVariable(os.VAR_callbacks);callbacks[$$PROP_length]>0;){var callback=callbacks.pop();if(callback.onAttach)callback.onAttach();else typeof callback=="function"&&callback[$$PROP_apply]({})}};
os.processTextContent_=function(fromNode,toNode){if(fromNode[$$PROP_childNodes][$$PROP_length]==1&&!toNode[$$PROP_getAttribute](os.ATT_customtag)&&fromNode[$$PROP_firstChild][$$PROP_nodeType]==3){var substitution=os.parseAttribute_(fromNode[$$PROP_firstChild][$$PROP_data]);substitution?toNode[$$PROP_setAttribute](ATT_content,substitution):toNode[$$PROP_appendChild](GLOBAL_document[$$PROP_createTextNode](os.trimWhitespaceForIE_(fromNode[$$PROP_firstChild][$$PROP_data],true,true)));return true}return false};
os.pushTextNode=function(array,text){text[$$PROP_length]>0&&array[$$PROP_push](GLOBAL_document[$$PROP_createTextNode](text))};os.trimWhitespaceForIE_=function(string,opt_trimStart,opt_trimEnd){if(os.isIe){var ret=string[$$PROP_replace](/[\x09-\x0d ]+/g," ");if(opt_trimStart)ret=ret[$$PROP_replace](/^\s/,"");if(opt_trimEnd)ret=ret[$$PROP_replace](/\s$/,"");return ret}return string};
os.breakTextNode_=function(textNode){for(var substRex=os.regExps_.VARIABLE_SUBSTITUTION,text=textNode[$$PROP_data],nodes=[],match=text[$$PROP_match](substRex);match;){match[1][$$PROP_length]>0&&os.pushTextNode(nodes,os.trimWhitespaceForIE_(match[1]));var token=match[2][$$PROP_substring](2,match[2][$$PROP_length]-1);token||(token=VAR_this);var tokenSpan=GLOBAL_document[$$PROP_createElement]("span");tokenSpan[$$PROP_setAttribute](ATT_content,os.transformExpression_(token));nodes[$$PROP_push](tokenSpan);
match=text[$$PROP_match](substRex);text=match[3];match=text[$$PROP_match](substRex)}text[$$PROP_length]>0&&os.pushTextNode(nodes,os.trimWhitespaceForIE_(text));return nodes};os.transformLiteral_=function(string){return"'"+string[$$PROP_replace](/'/g,"\\'")[$$PROP_replace](/\n/g," ")[$$PROP_replace](/;/g,"'+os.SEMICOLON+'")+"'"};
os.parseAttribute_=function(value){if(!value[$$PROP_length])return null;var substRex=os.regExps_.VARIABLE_SUBSTITUTION,text=value,parts=[],match=text[$$PROP_match](substRex);if(!match)return null;for(;match;){match[1][$$PROP_length]>0&&parts[$$PROP_push](os.transformLiteral_(os.trimWhitespaceForIE_(match[1],parts[$$PROP_length]==0)));var expr=match[2][$$PROP_substring](2,match[2][$$PROP_length]-1);expr||(expr=VAR_this);parts[$$PROP_push]("("+os.transformExpression_(expr)+")");text=match[3];match=
text[$$PROP_match](substRex)}text[$$PROP_length]>0&&parts[$$PROP_push](os.transformLiteral_(os.trimWhitespaceForIE_(text,false,true)));return parts[$$PROP_join]("+")};
os.getValueFromNode_=function(node,name){if(name=="*"){for(var children=[],child=node[$$PROP_firstChild];child;child=child[$$PROP_nextSibling])children[$$PROP_push](child);return children}if(name[$$PROP_indexOf](":")>=0)name=name[$$PROP_substring](name[$$PROP_indexOf](":")+1);var ret=node[name];if(typeof ret=="undefined"||ret==null)ret=node[$$PROP_getAttribute](name);if(typeof ret!="undefined"&&ret!=null){if(ret=="false")ret=false;else if(ret=="0")ret=0;return ret}var myMap=node[os.VAR_my];if(!myMap){myMap=
os.computeChildMap_(node);node[os.VAR_my]=myMap}return ret=myMap[name[$$PROP_toLowerCase]()]};os.identifiersNotToWrap_={};os.identifiersNotToWrap_["true"]=true;os.identifiersNotToWrap_["false"]=true;os.identifiersNotToWrap_["null"]=true;os.identifiersNotToWrap_["var"]=true;os.identifiersNotToWrap_[os.VAR_my]=true;os.identifiersNotToWrap_[VAR_this]=true;os.identifiersNotToWrap_[VAR_context]=true;os.identifiersNotToWrap_[VAR_top]=true;os.identifiersNotToWrap_[VAR_loop]=true;
os.canStartIdentifier=function(ch){return ch>="a"&&ch<="z"||ch>="A"&&ch<="Z"||ch=="_"||ch=="$"};os.canBeInIdentifier=function(ch){return os.canStartIdentifier(ch)||ch>="0"&&ch<="9"||ch==":"};os.canBeInToken=function(ch){return os.canBeInIdentifier(ch)||ch=="("||ch==")"||ch=="["||ch=="]"||ch=="."};
os.wrapSingleIdentifier=function(iden,opt_context,opt_default){if(os.identifiersNotToWrap_[$$PROP_hasOwnProperty](iden)&&(!opt_context||opt_context==VAR_context))return iden;return os.VAR_identifierresolver+"("+(opt_context||VAR_context)+", '"+iden+"'"+(opt_default?", "+opt_default:"")+")"};
os.wrapIdentifiersInToken=function(token,opt_default){if(!os.canStartIdentifier(token[$$PROP_charAt](0)))return token;if(token[$$PROP_substring](0,os.VAR_msg[$$PROP_length]+1)==os.VAR_msg+"."&&os.gadgetPrefs_){var key=token[$$PROP_split](".")[1],msg=os.getPrefMessage(key)||"";return os.parseAttribute_(msg)||os.transformLiteral_(msg)}for(var identifiers=os.tokenToIdentifiers(token),parts=false,buffer=[],output=null,i=0;i<identifiers[$$PROP_length];i++){var iden=identifiers[i];if(parts=os.breakUpParens(iden)){SETPROP_length(buffer,
0);buffer[$$PROP_push](os.wrapSingleIdentifier(parts[0],output));for(var j=1;j<parts[$$PROP_length];j+=3){buffer[$$PROP_push](parts[j]);parts[j+1]&&buffer[$$PROP_push](os.wrapIdentifiersInExpression(parts[j+1]));buffer[$$PROP_push](parts[j+2])}output=buffer[$$PROP_join]("")}else output=os.wrapSingleIdentifier(iden,output,opt_default)}return output};
os.wrapIdentifiersInExpression=function(expr,opt_default){for(var out=[],tokens=os.expressionToTokens(expr),i=0;i<tokens[$$PROP_length];i++)out[$$PROP_push](os.wrapIdentifiersInToken(tokens[i],opt_default));return out[$$PROP_join]("")};
os.expressionToTokens=function(expr){for(var tokens=[],inquotes=false,inidentifier=false,inparens=0,escaped=false,quotestart=null,buffer=[],i=0;i<expr[$$PROP_length];i++){var ch=expr[$$PROP_charAt](i);if(inquotes)if(!escaped&&ch==quotestart)inquotes=false;else escaped=ch=="\\"?true:false;else{if(ch=="'"||ch=='"'){inquotes=true;quotestart=ch;buffer[$$PROP_push](ch);continue}if(ch=="(")inparens++;else ch==")"&&inparens>0&&inparens--;if(inparens>0){buffer[$$PROP_push](ch);continue}if(!inidentifier&&
os.canStartIdentifier(ch)){if(buffer[$$PROP_length]>0){tokens[$$PROP_push](buffer[$$PROP_join](""));SETPROP_length(buffer,0)}inidentifier=true;buffer[$$PROP_push](ch);continue}if(inidentifier)if(!os.canBeInToken(ch)){tokens[$$PROP_push](buffer[$$PROP_join](""));SETPROP_length(buffer,0);inidentifier=false}}buffer[$$PROP_push](ch)}tokens[$$PROP_push](buffer[$$PROP_join](""));return tokens};
os.tokenToIdentifiers=function(token){for(var inquotes=false,quotestart=null,escaped=false,buffer=[],identifiers=[],i=0;i<token[$$PROP_length];i++){var ch=token[$$PROP_charAt](i);if(inquotes){if(!escaped&&ch==quotestart)inquotes=false;else escaped=ch=="\\"?true:false;buffer[$$PROP_push](ch)}else if(ch=="'"||ch=='"'){buffer[$$PROP_push](ch);inquotes=true;quotestart=ch}else if(ch=="."&&!inquotes){identifiers[$$PROP_push](buffer[$$PROP_join](""));SETPROP_length(buffer,0)}else buffer[$$PROP_push](ch)}identifiers[$$PROP_push](buffer[$$PROP_join](""));
return identifiers};
os.breakUpParens=function(identifier){var parenIndex=identifier[$$PROP_indexOf]("("),bracketIndex=identifier[$$PROP_indexOf]("[");if(parenIndex<0&&bracketIndex<0)return false;var parts=[];if(parenIndex<0||bracketIndex>=0&&bracketIndex<parenIndex){parenIndex=0;parts[$$PROP_push](identifier[$$PROP_substring](0,bracketIndex))}else{bracketIndex=0;parts[$$PROP_push](identifier[$$PROP_substring](0,parenIndex))}for(var parenstart=null,inquotes=false,quotestart=null,parenlevel=0,escaped=false,buffer=[],i=
bracketIndex+parenIndex;i<identifier[$$PROP_length];i++){var ch=identifier[$$PROP_charAt](i);if(inquotes){if(!escaped&&ch==quotestart)inquotes=false;else escaped=ch=="\\"?true:false;buffer[$$PROP_push](ch)}else if(ch=="'"||ch=='"'){inquotes=true;quotestart=ch;buffer[$$PROP_push](ch)}else if(parenlevel==0){if(ch=="("||ch=="["){parenstart=ch;parenlevel++;parts[$$PROP_push](ch);SETPROP_length(buffer,0)}}else if(parenstart=="("&&ch==")"||parenstart=="["&&ch=="]"){parenlevel--;if(parenlevel==0){parts[$$PROP_push](buffer[$$PROP_join](""));
parts[$$PROP_push](ch)}else buffer[$$PROP_push](ch)}else{ch==parenstart&&parenlevel++;buffer[$$PROP_push](ch)}}return parts};os.Loader={};os.Loader.loadedUrls_={};os.Loader.loadUrl=function(url,callback){typeof GLOBAL_window.gadgets!="undefined"?os.Loader.requestUrlGadgets_(url,callback):os.Loader.requestUrlXHR_(url,callback)};
os.Loader.requestUrlXHR_=function(url,callback){if(os.Loader.loadedUrls_[url])GLOBAL_window[$$PROP_setTimeout](callback,0);else{var req=null;req=typeof XMLHttpRequest!="undefined"?new XMLHttpRequest:new ActiveXObject("MSXML2.XMLHTTP");req.open("GET",url,true);req.onreadystatechange=function(){if(req.readyState==4){os.Loader.loadContent(req.responseText);os.Loader.loadedUrls_[url]=true;callback()}};req[$$PROP_send](null)}};
os.Loader.requestUrlGadgets_=function(url,callback){var params={},gadgets=GLOBAL_window.gadgets;if(os.Loader.loadedUrls_[url])GLOBAL_window[$$PROP_setTimeout](callback,0);else{params[gadgets.io.RequestParameters.CONTENT_TYPE]=gadgets.io.ContentType.TEXT;gadgets.io.makeRequest(url,function(obj){os.Loader.loadContent(obj[$$PROP_data]);os.Loader.loadedUrls_[url]=true;callback()},params)}};
os.Loader.loadUrls=function(urls,callback){var loadOne=function(){urls[$$PROP_length]==0?callback():os.Loader.loadUrl(urls.pop(),loadOne)};loadOne()};os.Loader.loadContent=function(xmlString){var doc=opensocial.xmlutil.parseXML(xmlString),templatesNode=doc[$$PROP_firstChild];os.Loader.processTemplatesNode(templatesNode)};os.Loader.getProcessorFunction_=function(tagName){return os.Loader["process"+tagName+"Node"]||null};
os.Loader.processTemplatesNode=function(node){for(var child=node[$$PROP_firstChild];child;child=child[$$PROP_nextSibling])if(child[$$PROP_nodeType]==1){var handler=os.Loader.getProcessorFunction_(child[$$PROP_tagName]);handler&&handler(child)}};os.Loader.processNamespaceNode=function(node){var prefix=node[$$PROP_getAttribute]("prefix"),url=node[$$PROP_getAttribute]("url");os.createNamespace(prefix,url)};
os.Loader.processTemplateDefNode=function(node){for(var tag=node[$$PROP_getAttribute]("tag"),name=node[$$PROP_getAttribute]("name"),child=node[$$PROP_firstChild];child;child=child[$$PROP_nextSibling])if(child[$$PROP_nodeType]==1){var handler=os.Loader.getProcessorFunction_(child[$$PROP_tagName]);handler&&handler(child,tag,name)}};
os.Loader.processTemplateNode=function(node,opt_tag,opt_name){var tag=opt_tag||node[$$PROP_getAttribute]("tag"),name=opt_name||node[$$PROP_getAttribute]("name");if(tag){var tagParts=tag[$$PROP_split](":");if(tagParts[$$PROP_length]!=2)throw"Invalid tag name: "+tag;var nsObj=os.getNamespace(tagParts[0]);if(!nsObj)throw"Namespace not registered: "+tagParts[0]+" while trying to define "+tag;var template=os.compileXMLNode(node);nsObj[tagParts[1]]=os.createTemplateCustomTag(template)}else if(name){template=
os.compileXMLNode(node);template.id=name;os.registerTemplate(template)}};os.Loader.processJavaScriptNode=function(node){for(var contentNode=node[$$PROP_firstChild];contentNode;contentNode=contentNode[$$PROP_nextSibling])os.Loader.injectJavaScript(contentNode[$$PROP_nodeValue])};os.Loader.processStyleNode=function(node){for(var contentNode=node[$$PROP_firstChild];contentNode;contentNode=contentNode[$$PROP_nextSibling])os.Loader.injectStyle(contentNode[$$PROP_nodeValue])};
os.Loader.headNode_=GLOBAL_document[$$PROP_getElementsByTagName]("head")[0]||GLOBAL_document[$$PROP_getElementsByTagName]("*")[0];os.Loader.injectJavaScript=function(jsCode){var scriptNode=GLOBAL_document[$$PROP_createElement]("script");scriptNode.type="text/javascript";scriptNode.text=jsCode;os.Loader.headNode_[$$PROP_appendChild](scriptNode)};
os.Loader.injectStyle=function(cssCode){var sheet;GLOBAL_document.styleSheets[$$PROP_length]==0&&GLOBAL_document[$$PROP_getElementsByTagName]("head")[0][$$PROP_appendChild](GLOBAL_document[$$PROP_createElement]("style"));sheet=GLOBAL_document.styleSheets[0];for(var rules=cssCode[$$PROP_split]("}"),i=0;i<rules[$$PROP_length];i++){var rule=rules[i][$$PROP_replace](/\n/g,"")[$$PROP_replace](/\s+/g," ");if(rule[$$PROP_length]>2)if(sheet.insertRule){rule+="}";sheet.insertRule(rule,sheet.cssRules[$$PROP_length])}else{var ruleParts=
rule[$$PROP_split]("{");sheet.addRule(ruleParts[0],ruleParts[1])}}};os.Container={};os.Container.inlineTemplates_=[];os.Container.domLoadCallbacks_=null;os.Container.domLoaded_=false;os.Container.autoProcess_=true;if(GLOBAL_window.gadgets){var params=GLOBAL_gadgets[$$PROP_util].getFeatureParameters("opensocial-templates");if(params&&params.disableAutoProcessing&&params.disableAutoProcessing[$$PROP_toLowerCase]!="false")os.Container.autoProcess_=false}os.Container.processed_=false;
os.Container.disableAutoProcessing=function(){if(os.Container.processed_)throw"Document already processed.";os.Container.autoProcess_=false};os.disableAutoProcessing=os.Container.disableAutoProcessing;
os.Container.registerDomLoadListener_=function(){var gadgets=GLOBAL_window.gadgets;if(gadgets&&gadgets[$$PROP_util])gadgets[$$PROP_util].registerOnLoadHandler(os.Container.onDomLoad_);else typeof GLOBAL_navigator!="undefined"&&GLOBAL_navigator.product&&GLOBAL_navigator.product=="Gecko"&&GLOBAL_window.addEventListener("DOMContentLoaded",os.Container.onDomLoad_,false);if(GLOBAL_window.addEventListener)GLOBAL_window.addEventListener("load",os.Container.onDomLoad_,false);else if(GLOBAL_document.body){var oldOnLoad=
GLOBAL_window.onload||function(){};GLOBAL_window.onload=function(){oldOnLoad();os.Container.onDomLoad_()}}else setTimeout(arguments.callee,0)};os.Container.onDomLoad_=function(){if(!os.Container.domLoaded_){for(;os.Container.domLoadCallbacks_[$$PROP_length];)try{os.Container.domLoadCallbacks_.pop()()}catch(e){os.log(e)}os.Container.domLoaded_=true}};
os.Container.executeOnDomLoad=function(callback){if(os.Container.domLoaded_)setTimeout(callback,0);else{if(os.Container.domLoadCallbacks_==null){os.Container.domLoadCallbacks_=[];os.Container.registerDomLoadListener_()}os.Container.domLoadCallbacks_[$$PROP_push](callback)}};
os.Container.registerDocumentTemplates=function(opt_doc){for(var doc=opt_doc||GLOBAL_document,nodes=doc[$$PROP_getElementsByTagName](os.Container.TAG_script_),i=0;i<nodes[$$PROP_length];++i){var node=nodes[i];if(os.Container.isTemplateType_(node[$$PROP_type])){var tag=node[$$PROP_getAttribute]("tag");if(tag)os.Container.registerTagElement_(node,tag);else node[$$PROP_getAttribute]("name")&&os.Container.registerTemplateElement_(node,node[$$PROP_getAttribute]("name"))}}};
os.Container.compileInlineTemplates=function(opt_data,opt_doc){for(var doc=opt_doc||GLOBAL_document,nodes=doc[$$PROP_getElementsByTagName](os.Container.TAG_script_),i=0;i<nodes[$$PROP_length];++i){var node=nodes[i];if(os.Container.isTemplateType_(node[$$PROP_type])){var name=node[$$PROP_getAttribute]("name")||node[$$PROP_getAttribute]("tag");if(!name||name[$$PROP_length]<0){var template=os.compileTemplate(node);template?os.Container.inlineTemplates_[$$PROP_push]({template:template,node:node}):os.warn("Failed compiling inline template.")}}}};
os.Container.defaultContext=null;os.Container.getDefaultContext=function(){if(!os.Container.defaultContext)os.Container.defaultContext=GLOBAL_window.gadgets&&GLOBAL_gadgets[$$PROP_util].hasFeature("opensocial-data")||opensocial[$$PROP_data].DataContext?os.createContext(opensocial[$$PROP_data].DataContext[$$PROP_getData]()):os.createContext({});return os.Container.defaultContext};
os.Container.renderInlineTemplates=function(opt_doc){for(var doc=opt_doc||GLOBAL_document,context=os.Container.getDefaultContext(),inlined=os.Container.inlineTemplates_,i=0;i<inlined[$$PROP_length];++i){var template=inlined[i].template,node=inlined[i].node,id="_T_"+template.id,el=doc[$$PROP_getElementById](id);if(!el){el=doc[$$PROP_createElement]("div");el[$$PROP_setAttribute]("id",id);node[$$PROP_parentNode].insertBefore(el,node)}if(GLOBAL_window.gadgets&&GLOBAL_gadgets[$$PROP_util].hasFeature("opensocial-data")||
opensocial[$$PROP_data].DataContext){var beforeData=node[$$PROP_getAttribute]("before")||node[$$PROP_getAttribute]("beforeData");if(beforeData){var keys=beforeData[$$PROP_split](/[\, ]+/);opensocial[$$PROP_data].DataContext.registerListener(keys,os.Container.createHideElementClosure(el))}var requiredData=node[$$PROP_getAttribute]("require")||node[$$PROP_getAttribute]("requireData");if(requiredData){keys=requiredData[$$PROP_split](/[\, ]+/);var callback=os.Container.createRenderClosure(template,el,
null,os.Container.getDefaultContext());"true"==node[$$PROP_getAttribute]("autoUpdate")?opensocial[$$PROP_data].DataContext.registerListener(keys,callback):opensocial[$$PROP_data].DataContext.registerOneTimeListener_(keys,callback)}else template.renderInto(el,null,context)}else template.renderInto(el,null,context)}};os.Container.createRenderClosure=function(template,element,opt_data,opt_context){var closure=function(){template.renderInto(element,opt_data,opt_context)};return closure};
os.Container.createHideElementClosure=function(element){var closure=function(){displayNone(element)};return closure};os.Container.registerTemplate=function(elementId){var element=GLOBAL_document[$$PROP_getElementById](elementId);return os.Container.registerTemplateElement_(element)};os.Container.registerTag=function(elementId){var element=GLOBAL_document[$$PROP_getElementById](elementId);os.Container.registerTagElement_(element,elementId)};
os.Container.renderElement=function(elementId,templateId,opt_data){var template=os.getTemplate(templateId);if(template){var element=GLOBAL_document[$$PROP_getElementById](elementId);element?template.renderInto(element,opt_data):os.warn("Element ("+elementId+") not found to render into.")}else os.warn("Template ("+templateId+") not registered.")};os.Container.processInlineTemplates=function(opt_doc){os.Container.compileInlineTemplates(opt_doc);os.Container.renderInlineTemplates(opt_doc)};
os.Container.processDocument=function(opt_doc){os.Container.registerDocumentTemplates(opt_doc);os.Container.processInlineTemplates(opt_doc);os.Container.processed_=true};os.process=os.Container.processDocument;os.Container.executeOnDomLoad(function(){os.Container.autoProcess_&&os.Container.processDocument()});os.Container.TAG_script_="script";os.Container.templateTypes_={};os.Container.templateTypes_["text/os-template"]=true;os.Container.templateTypes_["text/template"]=true;
os.Container.isTemplateType_=function(typeName){return os.Container.templateTypes_[typeName]!=null};os.Container.registerTemplateElement_=function(element,opt_id){var template=os.compileTemplate(element,opt_id);template?os.registerTemplate(template):os.warn("Could not compile template ("+element.id+")");return template};
os.Container.registerTagElement_=function(element,name){var template=os.Container.registerTemplateElement_(element);if(template){var tagParts=name[$$PROP_split](":"),nsObj=os.getNamespace(tagParts[0]);nsObj||(nsObj=os.createNamespace(tagParts[0],null));nsObj[tagParts[1]]=os.createTemplateCustomTag(template)}};
os.defineBuiltinTags=function(){function createClosure(object,method){return function(){method[$$PROP_apply](object)}}function processOnAttach(node,code,data,context){var callbacks=context.getVariable(os.VAR_callbacks),func=new Function(code);callbacks[$$PROP_push](createClosure(node,func))}var osn=os.getNamespace("os")||os.createNamespace("os","http://ns.opensocial.org/2008/markup");osn.Render=function(node,data,context){var parent=context.getVariable(os.VAR_parentnode),exp=node[$$PROP_getAttribute]("content")||
"*",result=os.getValueFromNode_(parent,exp);if(result)if(typeof result=="string"){var textNode=GLOBAL_document[$$PROP_createTextNode](result);result=[];result[$$PROP_push](textNode)}else if(os.isArray(result)){if(exp!="*"&&result[$$PROP_length]==1&&result[0][$$PROP_nodeType]==1){resultArray=[];for(var child=result[0][$$PROP_firstChild];child;child=child[$$PROP_nextSibling])resultArray[$$PROP_push](child);result=resultArray}}else{for(var resultArray=[],i=0;i<result[$$PROP_childNodes][$$PROP_length];i++)resultArray[$$PROP_push](result[$$PROP_childNodes][i]);
result=resultArray}else return"";if(os.isIe)for(i=0;i<result[$$PROP_length];i++)if(result[i][$$PROP_nodeType]==3){var trimmed=os.trimWhitespaceForIE_(result[i][$$PROP_nodeValue],i==0,i==result[$$PROP_length]-1);if(trimmed!=result[i][$$PROP_nodeValue]){result[i][$$PROP_parentNode][$$PROP_removeChild](result[i]);result[i]=GLOBAL_document[$$PROP_createTextNode](trimmed)}}return result};osn.render=osn.RenderAll=osn.renderAll=osn.Render;osn.Html=function(node){var html=node.code?""+node.code:node[$$PROP_getAttribute]("code")||
"";return html};os.registerAttribute_("onAttach",processOnAttach)};os.defineBuiltinTags();
os.resolveOpenSocialIdentifier=function(object,name){if(typeof object[name]!="undefined")return object[name];var functionName=os.getPropertyGetterName(name);if(object[functionName])return object[functionName]();if(object.getField){var fieldData=object.getField(name);if(fieldData)return fieldData}if(object.get){var responseItem=object.get(name);if(responseItem&&responseItem[$$PROP_getData]){var data=responseItem[$$PROP_getData]();return data.array_||data}return responseItem}var und;return und};os.setIdentifierResolver(os.resolveOpenSocialIdentifier);
os.createOpenSocialGetMethods_=function(object,fields){if(object&&fields)for(var key in fields){var value=fields[key],getter=os.getPropertyGetterName(value);object[$$PROP_prototype][getter]=function(){this.getField(key)}}};os.registerOpenSocialFields_=function(){};os.registerOpenSocialFields_();opensocial.template.Container.disableAutoProcessing();goog.friendconnect={};goog[$$PROP_friendconnect].newFetchInterestHintRequest=function(opt_max_hints,opt_seed){var rpcParams={command:"GetInterestHint",params:{maxHints:opt_max_hints?opt_max_hints:1,seed:opt_seed?opt_seed:0}},rpc={method:"extensions.get",params:rpcParams};return new JsonRpcRequestItem(rpc)};
goog[$$PROP_friendconnect].updateProfileExtensions=function(extensions,opt_callback){var person={},personExtensions={};person.profileExtensions=personExtensions;for(var extId in extensions){var extensionIn=extensions[extId];if(extensionIn.choiceId&&!goog.isArray(extensionIn.choiceId))extensionIn.choiceId=[extensionIn.choiceId];var extensionOut={};extensionOut.value=extensionIn;personExtensions[extId]=extensionOut}var rpc={method:"people.put",params:{}};rpc.params={};rpc.params.person=person;rpc.params.userId=
["@viewer"];var req=goog.peoplesense.newDataRequest();req.add(new JsonRpcRequestItem(rpc),"update");var callback=function(response){opt_callback&&opt_callback(response.get("update"))};req[$$PROP_send](callback)};goog[$$PROP_friendconnect].showMemberProfile=function(memberId,opt_siteId,opt_onCloseCallback){goog.peoplesense.PeopleSenseContainerImpl.showMemberProfile(memberId,opt_siteId,opt_onCloseCallback)};goog.exportSymbol("google.friendconnect.newFetchInterestHintRequest",goog[$$PROP_friendconnect].newFetchInterestHintRequest);
goog.exportSymbol("google.friendconnect.updateProfileExtensions",goog[$$PROP_friendconnect].updateProfileExtensions);goog.exportSymbol("google.friendconnect.showMemberProfile",goog[$$PROP_friendconnect].showMemberProfile);goog.peoplesense.DataRequest=function(){opensocial.DataRequest[$$PROP_call](this);this.addedRequestObjects_=[];this.finalCallback_=this.responseItem_=this.response_=this.requestObject_=null};goog.inherits(goog.peoplesense.DataRequest,opensocial.DataRequest);a=goog.peoplesense.DataRequest[$$PROP_prototype];a.add=function(req,opt_key){this.addedRequestObjects_[$$PROP_push]({key:opt_key,request:req});return goog.peoplesense.DataRequest.superClass_.add[$$PROP_call](this,req,opt_key)};
a.resendCallback_=function(signature){if(signature){this.requestObject_.rpc.params.sig=signature;var req=opensocial.newDataRequest();req.add(this.requestObject_,"internalRetry");var callback=goog[$$PROP_bind](this.resendDone_,this);req[$$PROP_send](callback)}goog[$$PROP_object][$$PROP_remove](this.responseItem_,"urlForApproval");goog[$$PROP_object][$$PROP_remove](this.responseItem_,"width");goog[$$PROP_object][$$PROP_remove](this.responseItem_,"height");goog[$$PROP_object][$$PROP_remove](this.responseItem_,
"title")};a.resendDone_=function(data){var newResponseItem=data.get("internalRetry");goog[$$PROP_object].clear(this.responseItem_);for(var key in newResponseItem)this.responseItem_[key]=newResponseItem[key];this.sendCallback_()};
a.sendCallback_=function(opt_callback,opt_response){if(opt_callback)this.finalCallback_=opt_callback;if(opt_response)this.response_=opt_response;for(var openedLightbox=false,i=0;i<this.addedRequestObjects_[$$PROP_length];++i){var entry=this.addedRequestObjects_[i],key=entry.key;if(key){this.requestObject_=entry.request;this.responseItem_=this.response_.get(key);var responseData=this.responseItem_[$$PROP_getData]();typeof GLOBAL_window.fcTiming!="undefined"&&GLOBAL_window.fcTiming.tick("post-"+key+
"-"+this.responseItem_.getOriginalDataRequest().rpc.params.command);if(responseData){var url=responseData.urlForApproval;if(url!=null){var width=responseData.width,height=responseData.height,title=responseData.title;openedLightbox=true;var postBody={};postBody.json=GLOBAL_gadgets.json.stringify(this.requestObject_.rpc);var callback=goog[$$PROP_bind](this.resendCallback_,this);goog.peoplesense.PeopleSenseContainerImpl.openLightboxIframe(url,null,width,height,title,postBody,GLOBAL_undefined,true,false,
GLOBAL_undefined,callback);break}}}}!openedLightbox&&this.finalCallback_&&this.finalCallback_(this.response_)};a.send=function(opt_callback){if(typeof GLOBAL_window.fcTiming!="undefined")for(var requestKey in this.getRequestObjects()){var request=this.getRequestObjects()[requestKey];GLOBAL_window.fcTiming.tick("pre-"+request.key+"-"+request.request.rpc.params.command)}var callback=goog[$$PROP_bind](this.sendCallback_,this,opt_callback);opensocial.Container.get().requestData(this,callback)};
goog.peoplesense.newDataRequest=function(){return new goog.peoplesense.DataRequest};goog.exportSymbol("google.friendconnect.DataRequest.prototype.send",goog.peoplesense.DataRequest[$$PROP_prototype][$$PROP_send]);goog.exportSymbol("opensocial.newDataRequest",goog.peoplesense.newDataRequest);goog.exportSymbol("goog.peoplesense.newDataRequest",goog.peoplesense.newDataRequest);goog.exportSymbol("opensocial",opensocial);goog.exportSymbol("shindig",shindig);
window['__fc_os_loaded__'] = true; 
 }})();google.friendconnect.container.setDateStamp_('12d143a13b6');