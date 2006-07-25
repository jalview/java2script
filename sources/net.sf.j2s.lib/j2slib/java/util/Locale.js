Clazz.load(["java.io.Serializable","java.lang.Cloneable"],"java.util.Locale",["java.lang.Character","$.InternalError","$.NullPointerException","$.StringBuffer","java.util.MissingResourceException","$.PropertyPermission","$.StringTokenizer"],function(){
c$=$_C(function(){
this.language="";
this.country="";
this.variant="";
this.hashcode=-1;
$_Z(this,arguments);
},java.util,"Locale",null,[Cloneable,java.io.Serializable]);
$_K(c$,
function(language,country,variant){
this.language=this.convertOldISOCodes(language);
this.country=country.toUpperCase();
this.variant=variant.intern();
},"~S,~S,~S");
$_K(c$,
function(language,country){
this.construct(language,country,"");
},"~S,~S");
$_K(c$,
function(language){
this.construct(language,"","");
},"~S");
c$.getDefault=$_M(c$,"getDefault",
function(){
if(java.util.Locale.defaultLocale==null){
var language="en";
var country="US";
var variant="";
navigator.userAgent.replace(/;\s*([a-zA-Z]{2,})[-_]([a-zA-Z]{2,});/,function($0,$1,$2){
language=$1;
country=$2;
});
java.util.Locale.defaultLocale=new java.util.Locale(language,country,variant);
}return java.util.Locale.defaultLocale;
});
c$.setDefault=$_M(c$,"setDefault",
function(newLocale){
if(newLocale==null)throw new java.lang.NullPointerException("Can't set default locale to NULL");
{
java.util.Locale.defaultLocale=newLocale;
}},"java.util.Locale");
c$.getAvailableLocales=$_M(c$,"getAvailableLocales",
function(){
var lcl=java.util.Locale;
return[
lcl.ENGLISH,
lcl.ENGLISH,
lcl.FRENCH,
lcl.GERMAN,
lcl.ITALIAN,
lcl.JAPANESE,
lcl.KOREAN,
lcl.CHINESE,
lcl.SIMPLIFIED_CHINESE,
lcl.TRADITIONAL_CHINESE,
lcl.FRANCE,
lcl.GERMANY,
lcl.ITALY,
lcl.JAPAN,
lcl.KOREA,
lcl.CHINA,
lcl.PRC,
lcl.TAIWAN,
lcl.UK,
lcl.US,
lcl.CANADA,
lcl.CANADA_FRENCH
];
});
c$.getISOCountries=$_M(c$,"getISOCountries",
function(){
if(java.util.Locale.isoCountries==null){
($t$=java.util.Locale.isoCountries=new Array(Math.floor(java.util.Locale.compressedIsoCountries.length/6)),java.util.Locale.prototype.isoCountries=java.util.Locale.isoCountries,$t$);
for(var i=0;i<java.util.Locale.isoCountries.length;i++)java.util.Locale.isoCountries[i]=java.util.Locale.compressedIsoCountries.substring((i*6)+1,(i*6)+3);

}var result=new Array(java.util.Locale.isoCountries.length);
System.arraycopy(java.util.Locale.isoCountries,0,result,0,java.util.Locale.isoCountries.length);
return result;
});
c$.getISOLanguages=$_M(c$,"getISOLanguages",
function(){
if(java.util.Locale.isoLanguages==null){
($t$=java.util.Locale.isoLanguages=new Array(Math.floor(java.util.Locale.compressedIsoLanguages.length/6)),java.util.Locale.prototype.isoLanguages=java.util.Locale.isoLanguages,$t$);
for(var i=0;i<java.util.Locale.isoLanguages.length;i++)java.util.Locale.isoLanguages[i]=java.util.Locale.compressedIsoLanguages.substring((i*6)+1,(i*6)+3);

}var result=new Array(java.util.Locale.isoLanguages.length);
System.arraycopy(java.util.Locale.isoLanguages,0,result,0,java.util.Locale.isoLanguages.length);
return result;
});
$_M(c$,"getLanguage",
function(){
return this.language;
});
$_M(c$,"getCountry",
function(){
return this.country;
});
$_M(c$,"getVariant",
function(){
return this.variant;
});
$_V(c$,"toString",
function(){
var l=this.language.length!=0;
var c=this.country.length!=0;
var v=this.variant.length!=0;
var result=new StringBuffer(this.language);
if(c||(l&&v)){
result.append('_').append(this.country);
}if(v&&(l||c)){
result.append('_').append(this.variant);
}return result.toString();
});
$_M(c$,"getISO3Language",
function(){
var length=this.language.length;
if(length==0){
return"";
}var index=java.util.Locale.compressedIsoLanguages.indexOf(","+this.language);
if(index==-1||length!=2){
throw new java.util.MissingResourceException("Couldn't find 3-letter language code for "+this.language,"LocaleElements_"+this.toString(),"ShortLanguage");
}return java.util.Locale.compressedIsoLanguages.substring(index+3,index+6);
});
$_M(c$,"getISO3Country",
function(){
var length=this.country.length;
if(length==0){
return"";
}var index=java.util.Locale.compressedIsoCountries.indexOf(","+this.country);
if(index==-1||length!=2){
throw new java.util.MissingResourceException("Couldn't find 3-letter country code for "+this.country,"LocaleElements_"+this.toString(),"ShortCountry");
}return java.util.Locale.compressedIsoCountries.substring(index+3,index+6);
});
$_M(c$,"getDisplayLanguage",
function(){
return this.getDisplayLanguage(java.util.Locale.getDefault());
});
$_M(c$,"getDisplayLanguage",
function(inLocale){
return inLocale.language;
},"java.util.Locale");
$_M(c$,"getDisplayCountry",
function(){
return this.getDisplayCountry(java.util.Locale.getDefault());
});
$_M(c$,"getDisplayCountry",
function(inLocale){
return inLocale.country;
},"java.util.Locale");
$_M(c$,"getDisplayVariant",
function(){
return this.getDisplayVariant(java.util.Locale.getDefault());
});
$_M(c$,"getDisplayVariant",
function(inLocale){
return inLocale.variant;
},"java.util.Locale");
$_M(c$,"getDisplayName",
function(){
return this.getDisplayName(java.util.Locale.getDefault());
});
$_M(c$,"getDisplayName",
function(inLocale){
var s=inLocale.language+"_"+inLocale.country;
var v=inLocale.variant;
if(v!=null&&v.length!=0){
return s+"("+v+")";
}else{
return s;
}
},"java.util.Locale");
$_M(c$,"clone",
function(){
try{
var that=$_U(this,java.util.Locale,"clone",[]);
return that;
}catch(e){
if($_O(e,java.lang.CloneNotSupportedException)){
throw new java.lang.InternalError();
}else{
throw e;
}
}
});
$_V(c$,"hashCode",
function(){
if(this.hashcode==-1){
this.hashcode=this.language.hashCode()^this.country.hashCode()^this.variant.hashCode();
}return this.hashcode;
});
$_V(c$,"equals",
function(obj){
if(this==obj)return true;
if(!($_O(obj,java.util.Locale)))return false;
var other=obj;
if(this.hashCode()!=other.hashCode())return false;
if(this.language!=other.language)return false;
if(this.country!=other.country)return false;
if(this.variant!=other.variant)return false;
return true;
},"~O");
$_M(c$,"convertOldISOCodes",
($fz=function(language){
language=language.toLowerCase();
if(language=="he"){
return"iw";
}else if(language=="yi"){
return"ji";
}else if(language=="id"){
return"in";
}else{
return language;
}},$fz.isPrivate=true,$fz),"~S");
c$.ENGLISH=c$.prototype.ENGLISH=new java.util.Locale("en","","");
c$.FRENCH=c$.prototype.FRENCH=new java.util.Locale("fr","","");
c$.GERMAN=c$.prototype.GERMAN=new java.util.Locale("de","","");
c$.ITALIAN=c$.prototype.ITALIAN=new java.util.Locale("it","","");
c$.JAPANESE=c$.prototype.JAPANESE=new java.util.Locale("ja","","");
c$.KOREAN=c$.prototype.KOREAN=new java.util.Locale("ko","","");
c$.CHINESE=c$.prototype.CHINESE=new java.util.Locale("zh","","");
c$.SIMPLIFIED_CHINESE=c$.prototype.SIMPLIFIED_CHINESE=new java.util.Locale("zh","CN","");
c$.TRADITIONAL_CHINESE=c$.prototype.TRADITIONAL_CHINESE=new java.util.Locale("zh","TW","");
c$.FRANCE=c$.prototype.FRANCE=new java.util.Locale("fr","FR","");
c$.GERMANY=c$.prototype.GERMANY=new java.util.Locale("de","DE","");
c$.ITALY=c$.prototype.ITALY=new java.util.Locale("it","IT","");
c$.JAPAN=c$.prototype.JAPAN=new java.util.Locale("ja","JP","");
c$.KOREA=c$.prototype.KOREA=new java.util.Locale("ko","KR","");
c$.CHINA=c$.prototype.CHINA=new java.util.Locale("zh","CN","");
c$.PRC=c$.prototype.PRC=new java.util.Locale("zh","CN","");
c$.TAIWAN=c$.prototype.TAIWAN=new java.util.Locale("zh","TW","");
c$.UK=c$.prototype.UK=new java.util.Locale("en","GB","");
c$.US=c$.prototype.US=new java.util.Locale("en","US","");
c$.CANADA=c$.prototype.CANADA=new java.util.Locale("en","CA","");
c$.CANADA_FRENCH=c$.prototype.CANADA_FRENCH=new java.util.Locale("fr","CA","");
$_S(c$,
"serialVersionUID",9149081749638150636,
"defaultLocale",null,
"isoLanguages",null,
"compressedIsoLanguages",",aaaar,ababk,afafr,amamh,arara,asasm,ayaym,azaze,babak,bebel,bgbul,bhbih,bibis,bnben,"+"bobod,brbre,cacat,cocos,csces,cycym,dadan,dedeu,dzdzo,elell,eneng,eoepo,esspa,"+"etest,eueus,fafas,fifin,fjfij,fofao,frfra,fyfry,gagai,gdgdh,glglg,gngrn,guguj,"+"hahau,heheb,hihin,hrhrv,huhun,hyhye,iaina,idind,ieile,ikipk,inind,isisl,itita,"+"iuiku,iwheb,jajpn,jiyid,jwjaw,kakat,kkkaz,klkal,kmkhm,knkan,kokor,kskas,kukur,"+"kykir,lalat,lnlin,lolao,ltlit,lvlav,mgmlg,mimri,mkmkd,mlmal,mnmon,momol,mrmar,"+"msmsa,mtmlt,mymya,nanau,nenep,nlnld,nonor,ococi,omorm,orori,papan,plpol,pspus,"+"ptpor,quque,rmroh,rnrun,roron,rurus,rwkin,sasan,sdsnd,sgsag,shsrp,sisin,skslk,"+"slslv,smsmo,snsna,sosom,sqsqi,srsrp,ssssw,stsot,susun,svswe,swswa,tatam,tetel,"+"tgtgk,ththa,titir,tktuk,tltgl,tntsn,toton,trtur,tstso,tttat,twtwi,uguig,ukukr,"+"ururd,uzuzb,vivie,vovol,wowol,xhxho,yiyid,yoyor,zazha,zhzho,zuzul",
"isoCountries",null,
"compressedIsoCountries",",ADAND,AEARE,AFAFG,AGATG,AIAIA,ALALB,AMARM,ANANT,AOAGO,AQATA,ARARG,ASASM,ATAUT,"+"AUAUS,AWABW,AZAZE,BABIH,BBBRB,BDBGD,BEBEL,BFBFA,BGBGR,BHBHR,BIBDI,BJBEN,BMBMU,"+"BNBRN,BOBOL,BRBRA,BSBHS,BTBTN,BVBVT,BWBWA,BYBLR,BZBLZ,CACAN,CCCCK,CFCAF,CGCOG,"+"CHCHE,CICIV,CKCOK,CLCHL,CMCMR,CNCHN,COCOL,CRCRI,CUCUB,CVCPV,CXCXR,CYCYP,CZCZE,"+"DEDEU,DJDJI,DKDNK,DMDMA,DODOM,DZDZA,ECECU,EEEST,EGEGY,EHESH,ERERI,ESESP,ETETH,"+"FIFIN,FJFJI,FKFLK,FMFSM,FOFRO,FRFRA,FXFXX,GAGAB,GBGBR,GDGRD,GEGEO,GFGUF,GHGHA,"+"GIGIB,GLGRL,GMGMB,GNGIN,GPGLP,GQGNQ,GRGRC,GSSGS,GTGTM,GUGUM,GWGNB,GYGUY,HKHKG,"+"HMHMD,HNHND,HRHRV,HTHTI,HUHUN,IDIDN,IEIRL,ILISR,ININD,IOIOT,IQIRQ,IRIRN,ISISL,"+"ITITA,JMJAM,JOJOR,JPJPN,KEKEN,KGKGZ,KHKHM,KIKIR,KMCOM,KNKNA,KPPRK,KRKOR,KWKWT,"+"KYCYM,KZKAZ,LALAO,LBLBN,LCLCA,LILIE,LKLKA,LRLBR,LSLSO,LTLTU,LULUX,LVLVA,LYLBY,"+"MAMAR,MCMCO,MDMDA,MGMDG,MHMHL,MKMKD,MLMLI,MMMMR,MNMNG,MOMAC,MPMNP,MQMTQ,MRMRT,"+"MSMSR,MTMLT,MUMUS,MVMDV,MWMWI,MXMEX,MYMYS,MZMOZ,NANAM,NCNCL,NENER,NFNFK,NGNGA,"+"NINIC,NLNLD,NONOR,NPNPL,NRNRU,NUNIU,NZNZL,OMOMN,PAPAN,PEPER,PFPYF,PGPNG,PHPHL,"+"PKPAK,PLPOL,PMSPM,PNPCN,PRPRI,PTPRT,PWPLW,PYPRY,QAQAT,REREU,ROROM,RURUS,RWRWA,"+"SASAU,SBSLB,SCSYC,SDSDN,SESWE,SGSGP,SHSHN,SISVN,SJSJM,SKSVK,SLSLE,SMSMR,SNSEN,"+"SOSOM,SRSUR,STSTP,SVSLV,SYSYR,SZSWZ,TCTCA,TDTCD,TFATF,TGTGO,THTHA,TJTJK,TKTKL,"+"TMTKM,TNTUN,TOTON,TPTMP,TRTUR,TTTTO,TVTUV,TWTWN,TZTZA,UAUKR,UGUGA,UMUMI,USUSA,"+"UYURY,UZUZB,VAVAT,VCVCT,VEVEN,VGVGB,VIVIR,VNVNM,VUVUT,WFWLF,WSWSM,YEYEM,YTMYT,"+"YUYUG,ZAZAF,ZMZMB,ZRZAR,ZWZWE");
});
