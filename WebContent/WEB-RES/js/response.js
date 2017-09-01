/*
 * 封装基本常用组件
 * 树
 * 表格
 * 下拉菜单
 * 按钮
 * 提示框
 * 面板
 * 选项卡
 * 滚动条
 * 搜索框
 * 按钮
 * 布局
 * 导航
 * 加密
 * ajax
 * md5
 * fileupload
 */

/* MainFunction BEGIN */
 
	
'use strict'

 
function getUrl(){
	var str = document.URL;
	var re = /.*?\:\/\/.*?\/.*?\//i;
	var url = str.match( re );
	return url[ 0 ];
}

var doc = $( document ),
	
	_url = getUrl(),
	folder_a = 'WEB-RES/',
	folder_b = 'page_zh-cn/',
	folder_c = 'nav-child-page/',
	folder_d = '',
	folder_e = '.html',
	folder_f = 'main-page/',
	folder_g = 'home',
	folder_h = 'content-child-page/',
	folder_l = 'error-page/',
	folder_p = 'customer-page/',
	folder_q = 'customerexcute',
	folder_m = '404',
	folder_n = '500',
	folder_o = '505',
	word_a = '',
	word_b = '',
	word_c = '信息格式有误',
	word_d = '无法连接服务器',
	word_e = '系统错误',
	clearspace = /\s/g;










/* 上传文件 BEGIN */
jQuery.extend({
    createUploadIframe : function ( id , uri ) {		/*id为当前系统时间字符串，uri是外部传入的json对象的一个参数*/
        /* 创建iframe用于接收结果 */
        var frameId = 'jUploadFrame' + id; 				/*给iframe添加一个独一无二的id*/
        var iframeHtml = '<iframe id="' + frameId + '" name="' + frameId + '" style="position:absolute; top:-9999px; left:-9999px"'; /*创建iframe元素*/
        if ( window.ActiveXObject ) {					/*判断浏览器是否支持ActiveX控件*/
            if ( typeof uri == 'boolean' ) {
                iframeHtml += ' src="' + 'javascript:false' + '"';
            } else if ( typeof uri == 'string' ) {
                iframeHtml += ' src="' + uri + '"';
            }
        }
        iframeHtml += ' />';
        jQuery( iframeHtml ).appendTo( document.body ); /*将动态iframe追加到body中*/
        return jQuery( '#' + frameId ).get( 0 ); 		/*返回iframe对象*/
    },
    createUploadForm : function ( id , fileElementId , data ) {		/*id为当前系统时间字符串，fileElementId为页面<input type='file' />的id，data的值需要根据传入json的键来决定*/
        /* 创建form表单 */
        var formId = 'jUploadForm' + id; 				/*给form添加一个独一无二的id */
        var fileId = 'jUploadFile' + id; 				/*给<input type='file' />添加一个独一无二的id */
        var form = jQuery( '<form  action="" method="POST" name="' + formId + '" id="' + formId + '" enctype="multipart/form-data" ></form>' ); 	/*创建form元素*/
        if ( data ) {									/*通常为false*/
            for ( var i in data ) {
                jQuery( '<input type="hidden" name="' + i + '" value="' + data[ i ] + '" />' ).appendTo( form ); 		/*根据data的内容，创建隐藏域，这部分我还不知道是什么时候用到。估计是传入json的时候，如果默认传一些参数的话要用到*/
            }
        }
		var oldElement = jQuery( '#' + fileElementId ); /*得到页面中的<input type='file' />对象*/
        var newElement = jQuery( oldElement ).clone(); 	/*克隆页面中的<input type='file' />对象*/
        jQuery( oldElement ).attr( 'id' , fileId ); 	/*修改原对象的id*/
        jQuery( oldElement ).before( newElement ); 		/*在原对象前插入克隆对象*/
        jQuery( oldElement ).appendTo( form ); 			/*把原对象插入到动态form的结尾处*/
        
        jQuery( form ).css( 'position' , 'absolute' ); 	/*给动态form添加样式，使其浮动起来*/
        jQuery( form ).css( 'top' , '-2200px' );
        jQuery( form ).css( 'left' , '-2200px' );
        jQuery( form ).appendTo( 'body' );
        return form;
    },
	/*handleError: function( s, xhr, status, e ) 		{
    	
		if ( s.error ) {
			s.error.call( s.context || s, xhr, status, e );
		}
		if ( s.global ) {
			(s.context ? jQuery(s.context) : jQuery.event).trigger( "ajaxError", [xhr, s, e] );
		}
    },*/
    ajaxFileUpload : function ( s ) {							/*这里s是个json对象，传入一些ajax的参数*/
        /* TODO introduce global settings, allowing the client to modify them for all requests, not only timeout*/        
        s = jQuery.extend( {} , jQuery.ajaxSettings , s ); 		/*此时的s对象是由jQuery.ajaxSettings和原s对象扩展后的对象*/
        var id = new Date().getTime(); 							/*取当前系统时间，目的是得到一个独一无二的数字*/
        var form = jQuery.createUploadForm( id , s.fileElementId , ( typeof ( s.data ) == 'undefined' ? false : s.data ) ); 	/*创建动态form*/
        var io = jQuery.createUploadIframe( id , s.secureuri ); /*创建动态iframe*/
        var frameId = 'jUploadFrame' + id; 						/*动态iframe的id*/
        var formId = 'jUploadForm' + id; 						/*动态form的id*/
        /* Watch for a new set of requests*/
        if ( s.global && !jQuery.active++ ) {					/*当jQuery开始一个ajax请求时发生*/
            jQuery.event.trigger( "ajaxStart" ); 				/*触发ajaxStart方法*/
        }        
		var requestDone = false; 								/*请求完成标志*/
        /* Create the request object*/
        var xml = {};        
		if ( s.global )
            jQuery.event.trigger( "ajaxSend" , [ xml , s ] ); 	/*触发ajaxSend方法*/
        /* Wait for a response to come back*/
        var uploadCallback = function ( isTimeout ) {			/*回调函数*/
            var io = document.getElementById( frameId ); 		/*得到iframe对象*/
            try {               
			    if ( io.contentWindow ) {						/*动态iframe所在窗口对象是否存在*/
                    xml.responseText = io.contentWindow.document.body ? io.contentWindow.document.body.innerHTML : null;
                    xml.responseXML = io.contentWindow.document.XMLDocument ? io.contentWindow.document.XMLDocument : io.contentWindow.document;
                } else if ( io.contentDocument ) {				/*动态iframe的文档对象是否存在*/
                    xml.responseText = io.contentDocument.document.body ? io.contentDocument.document.body.innerHTML : null;
                    xml.responseXML = io.contentDocument.document.XMLDocument ? io.contentDocument.document.XMLDocument : io.contentDocument.document;
                }
            } catch ( e ) {
                jQuery.handleError( s , xml , null , e );
            }            
			if ( xml || isTimeout == "timeout" ) {				/*xml变量被赋值或者isTimeout == "timeout"都表示请求发出，并且有响应*/
                requestDone = true; 							/*请求完成*/
                var status;                
				try {
                    status = isTimeout != "timeout" ? "success" : "error"; 		/*如果不是"超时"，表示请求成功*/
                    
                    if ( status != "error" ) {                 					/* process the data (runs the xml through httpData regardless of callback)*/
                        var data = jQuery.uploadHttpData( xml , s.dataType ); 	/*根据传送的type类型，返回json对象，此时返回的data就是后台操作后的返回结果*/
                        
                        if ( s.success ) {
							s.success( data , status ); 						/*执行上传成功的操作*/
						}
                        if ( s.global ) {
							jQuery.event.trigger( "ajaxSuccess" , [ xml , s ] );
						}
                    } else{
						jQuery.handleError( s , xml , status );
					}
                } catch ( e ) {
                    status = "error";
                    jQuery.handleError( s , xml , status , e );					/* 此处 */
                }                
				
                if ( s.global )
                    jQuery.event.trigger( "ajaxComplete" , [ xml , s ] );		/* Handle the global AJAX counter*/
                if ( s.global && ! --jQuery.active )
                    jQuery.event.trigger( "ajaxStop" );                			/* Process result*/
                if ( s.complete )
                    s.complete( xml , status );
                jQuery( io ).unbind();											/*移除iframe的事件处理程序*/
                setTimeout( function () {										/*设置超时时间*/
                    try {
                        jQuery( io ).remove();									/*移除动态iframe*/
                        jQuery( form ).remove();								/*移除动态form*/
                    } catch ( e ) {
                        jQuery.handleError( s , xml , null , e );
                    }
                }, 100 )
                xml = null
            }
        }
		
        if ( s.timeout > 0 ) {							/*超时检测*/
            setTimeout( function () {                	/* Check to see if the request is still happening*/
                if ( !requestDone ) 
					uploadCallback( "timeout" );		/*如果请求仍未完成，就发送超时信号*/
            } , s.timeout );
        }        
		try {            
			var form = jQuery( '#' + formId );
            jQuery( form ).attr( 'action' , s.url );	/*传入的ajax页面导向url*/
            jQuery( form ).attr( 'method' , 'POST' );	/*设置提交表单方式*/
            jQuery( form ).attr( 'target' , frameId );	/*返回的目标iframe，就是创建的动态iframe*/
            if ( form.encoding ) {						/*选择编码方式*/
                jQuery( form ).attr( 'encoding' , 'multipart/form-data' );
            } else {
                jQuery( form ).attr( 'enctype' , 'multipart/form-data' );
            }
            jQuery( form ).submit();
        } catch ( e ) {
            jQuery.handleError( s , xml , null , e );
        }
        jQuery( '#' + frameId ).load( uploadCallback ); /*ajax 请求从服务器加载数据，同时传入回调函数*/
        return { 
			abort: function () { }
		};
    },
    uploadHttpData : function ( r , type ) {
	    var data = !type;
        data = type == "xml" || data ? r.responseXML : r.responseText;
        if ( type == "script" ) {
			jQuery.globalEval( data );
		}
        if ( type == "json" ) {
			data = eval( jQuery.parseJSON( ( ( data ).replace( '<pre>' , '' ) ).replace( '</pre>' , '' ) ) );
		}
        if ( type == "html" ) {
			jQuery( "<div>" ).html( data ).evalScripts();
		} 
		return data;
    }
});

/*调用实例*/
/*$.ajaxFileUpload({
	url : '../../XXXX/XXXX.aspx', 			//用于文件上传的服务器端请求地址
	secureuri : false,           			//一般设置为false
	fileElementId : id, 					//文件上传控件的id属性  <input type="file" id="file" name="file" /> 这里一定要有name值  $("form").serialize(),表单序列化。指把所有元素的ID，NAME 等全部发过去
	dataType : 'json',						//返回值类型 一般设置为json
	complete : function () {				//只要完成即执行，最后执行	
	},										
	success : function ( data , status ){ 	//服务器成功响应处理函数          
		if ( typeof ( data.error ) != 'undefined') {                
			if ( data.error != '' ) {                    
				if ( data.error == "1001" ) {
					//这个error（错误码）是由自己定义的，根据后台返回的json对象的键值而判断
				} else if ( data.error == "1002" ) {
				
				}
				
				return;
			} else {
				
			}
		}            
		//  这里就是做一些其他操作，比如把图片显示到某控件中去之类的。/
	},
	error : function ( data , status , e ) { //服务器响应失败处理函数
		
	}
})*/
/* 上传文件 END */


/* md5加密 BEGIN */
var hexcase = 0,
	b64pad  = "",
	chrsz   = 8;  
function hex_md5( s ){ 
	return binl2hex( core_md5( str2binl( s ) , s.length * chrsz ) );
}
function b64_md5( s ){ 
	return binl2b64( core_md5( str2binl( s ) , s.length * chrsz ) );
}
function hex_hmac_md5( key , data ) { 
	return binl2hex( core_hmac_md5( key , data ) ); 
}
function b64_hmac_md5( key , data ) { 
	return binl2b64( core_hmac_md5( key , data ) ); 
}
function calcMD5( s ){ 
	return binl2hex( core_md5( str2binl( s ) , s.length * chrsz ) );
}

function md5_vm_test(){
  return hex_md5( "abc" ) == "900150983cd24fb0d6963f7d28e17f72";
}

function core_md5( x , len ){
	x[ len >> 5 ] |= 0x80 << ( ( len) % 32 );
	x[ ( ( ( len + 64 ) >>> 9 ) << 4 ) + 14 ] = len;
	var a =  1732584193,
		b = -271733879,
  		c = -1732584194,
  		d =  271733878;
  for ( var i = 0 ; i < x.length ; i += 16 ) {
		var olda = a,
			oldb = b,
			oldc = c,
			oldd = d;
		a = md5_ff( a , b , c , d , x[ i + 0] , 7 , -680876936 );
    	d = md5_ff( d , a , b , c , x[ i + 1] , 12 , -389564586 );
    	c = md5_ff( c , d , a , b , x[ i + 2 ] , 17 ,  606105819 );
    	b = md5_ff( b , c , d , a , x[ i + 3 ] , 22 , -1044525330 );
		a = md5_ff( a , b , c , d , x[ i + 4 ] , 7 , -176418897 );
		d = md5_ff( d , a , b , c , x[ i + 5 ] , 12 ,  1200080426 );
		c = md5_ff( c , d , a , b , x[ i + 6 ] , 17 , -1473231341 );
		b = md5_ff( b , c , d , a , x[ i + 7 ] , 22 , -45705983 );
		a = md5_ff( a , b , c , d , x[ i + 8 ], 7 ,  1770035416 );
		d = md5_ff( d , a , b , c , x[ i + 9 ] , 12 , -1958414417 );
		c = md5_ff( c , d , a , b , x[ i + 10 ] , 17 , -42063 );
		b = md5_ff( b , c , d , a , x[ i + 11 ] , 22 , -1990404162 );
		a = md5_ff( a , b , c , d , x[ i + 12 ] , 7 ,  1804603682 );
		d = md5_ff( d , a , b , c , x[ i + 13 ] , 12 , -40341101 );
		c = md5_ff( c , d , a , b , x[ i + 14 ] , 17 , -1502002290 );
		b = md5_ff( b , c , d , a , x[ i + 15 ] , 22 ,  1236535329 );
		a = md5_gg( a , b , c , d , x[ i + 1 ] , 5 , -165796510 );
		d = md5_gg( d , a , b , c , x[ i + 6 ] , 9 , -1069501632 );
		c = md5_gg( c , d , a , b , x[ i + 11 ] , 14 ,  643717713 );
		b = md5_gg( b , c , d , a , x[ i + 0 ] , 20 , -373897302 );
		a = md5_gg( a , b , c , d , x[ i + 5 ] , 5 , -701558691 );
		d = md5_gg( d , a , b , c , x[ i + 10 ] , 9 ,  38016083 );
		c = md5_gg( c , d , a , b , x[ i + 15 ] , 14 , -660478335 );
		b = md5_gg( b , c , d , a , x[ i + 4 ] , 20 , -405537848 );
		a = md5_gg( a , b , c , d , x[ i + 9 ] , 5 ,  568446438 );
		d = md5_gg( d , a , b , c , x[ i + 14 ] , 9 , -1019803690 );
		c = md5_gg( c , d , a , b , x[ i + 3 ] , 14 , -187363961 );
		b = md5_gg( b , c , d , a , x[ i + 8] , 20 ,  1163531501 );
		a = md5_gg( a , b , c , d , x[ i + 13 ] , 5 , -1444681467 );
		d = md5_gg( d , a , b , c , x[ i + 2] , 9 , -51403784 );
		c = md5_gg( c , d , a , b , x[ i + 7 ] , 14 ,  1735328473 );
		b = md5_gg( b , c , d , a , x[ i + 12 ] , 20 , -1926607734 );
		a = md5_hh( a , b , c , d , x[ i + 5 ] , 4 , -378558 );
		d = md5_hh( d , a , b , c , x[ i + 8 ] , 11 , -2022574463 );
		c = md5_hh( c , d , a , b , x[ i + 11 ] , 16 ,  1839030562 );
		b = md5_hh( b , c , d , a, x[ i + 14 ] , 23 , -35309556 );
		a = md5_hh( a , b , c , d , x[ i + 1 ] , 4 , -1530992060 );
		d = md5_hh( d , a , b , c , x[ i + 4 ] , 11 ,  1272893353 );
		c = md5_hh( c , d , a , b , x[ i + 7 ] , 16 , -155497632 );
		b = md5_hh( b , c , d , a , x[ i + 10 ] , 23 , -1094730640 );
		a = md5_hh( a , b , c , d , x[ i + 13 ] , 4 ,  681279174 );
		d = md5_hh( d , a , b , c , x[ i + 0 ] , 11 , -358537222 );
		c = md5_hh( c , d , a , b , x[ i + 3 ] , 16 , -722521979 );
		b = md5_hh( b , c , d , a , x[ i + 6 ] , 23 ,  76029189 );
		a = md5_hh( a , b , c , d , x[ i + 9 ] , 4 , -640364487 );
		d = md5_hh( d , a , b , c , x[ i + 12 ] , 11 , -421815835 );
		c = md5_hh( c , d , a , b , x[ i + 15 ] , 16 ,  530742520 );
		b = md5_hh( b , c , d , a , x[ i + 2 ] , 23 , -995338651 );
		a = md5_ii( a , b , c , d , x[ i + 0 ] , 6 , -198630844 );
		d = md5_ii( d , a , b , c , x[ i + 7 ] , 10 ,  1126891415 );
		c = md5_ii( c , d , a , b , x[ i + 14 ] , 15 , -1416354905 );
		b = md5_ii( b , c , d , a , x[ i + 5 ] , 21 , -57434055 );
		a = md5_ii( a , b , c , d , x[ i + 12 ] , 6 ,  1700485571 );
		d = md5_ii( d , a , b , c , x[ i + 3 ] , 10 , -1894986606 );
		c = md5_ii( c , d , a , b , x[ i + 10 ] , 15 , -1051523 );
		b = md5_ii( b , c , d , a , x[ i + 1 ] , 21 , -2054922799 );
		a = md5_ii( a , b , c , d , x[ i + 8 ] , 6 ,  1873313359 );
		d = md5_ii( d , a , b , c , x[ i + 15 ] , 10 , -30611744 );
		c = md5_ii( c , d , a , b , x[ i + 6 ] , 15 , -1560198380 );
		b = md5_ii( b , c , d , a , x[ i + 13 ] , 21 ,  1309151649 );
		a = md5_ii( a , b , c , d , x[ i + 4 ] , 6 , -145523070 );
		d = md5_ii( d , a , b , c , x[ i + 11 ] , 10 , -1120210379 );
		c = md5_ii( c , d , a , b , x[ i + 2 ] , 15 ,  718787259 );
		b = md5_ii( b , c , d , a , x[ i + 9 ] , 21 , -343485551 );
	
		a = safe_add( a , olda );
		b = safe_add( b , oldb );
		c = safe_add( c , oldc );
		d = safe_add( d , oldd );
	}
	return Array( a , b , c , d );
  
}
function md5_cmn( q , a , b , x , s , t ){
	return safe_add( bit_rol( safe_add( safe_add( a , q ) , safe_add( x , t ) ) , s ) , b );
}
function md5_ff( a , b , c , d , x , s , t ){
	return md5_cmn( ( b & c ) | ( ( ~b ) & d ) , a , b , x , s , t );
}
function md5_gg( a , b , c , d , x , s , t ){
	return md5_cmn( ( b & d ) | ( c & ( ~d ) ) , a , b , x , s , t );
}
function md5_hh( a , b , c , d , x , s , t ){
	return md5_cmn( b ^ c ^ d , a , b , x , s , t );
}
function md5_ii( a , b , c , d , x , s , t ){
	return md5_cmn( c ^ ( b | ( ~d ) ) , a , b , x , s , t );
}
function core_hmac_md5( key , data ){
	var bkey = str2binl( key );
	if ( bkey.length > 16 ) bkey = core_md5( bkey , key.length * chrsz );
	var ipad = Array( 16 ), 
		opad = Array( 16 );
	for ( var i = 0; i < 16; i++ ) {
		ipad[ i ] = bkey[ i ] ^ 0x36363636;
    	opad[ i ] = bkey[ i ] ^ 0x5C5C5C5C;
	}
  	var hash = core_md5( ipad.concat( str2binl( data ) ) , 512 + data.length * chrsz );
  	return core_md5( opad.concat( hash ) , 512 + 128 );
}
function safe_add( x , y ) {
	var lsw = ( x & 0xFFFF ) + ( y & 0xFFFF );
  	var msw = ( x >> 16 ) + ( y >> 16 ) + ( lsw >> 16 );
  	return ( msw << 16 ) | ( lsw & 0xFFFF );
}
function bit_rol( num , cnt ) {
  	return ( num << cnt ) | ( num >>> ( 32 - cnt ) );
}
function str2binl(str){
  	var bin = Array();
  	var mask = (1 << chrsz) - 1;
  	for(var i = 0; i < str.length * chrsz; i += chrsz)
    	bin[ i >> 5 ] |= ( str.charCodeAt( i / chrsz ) & mask ) << ( i%32 );
  	return bin;
}

function binl2hex( binarray ){
  	var hex_tab = hexcase ? "0123456789ABCDEF" : "0123456789abcdef";
  	var str = "";
  	for( var i = 0; i < binarray.length * 4; i++ ) {
    	str += hex_tab.charAt( ( binarray[ i >> 2 ] >> ( ( i%4 ) * 8 + 4 ) ) & 0xF ) + hex_tab.charAt( ( binarray[ i >> 2 ] >> ( ( i%4 ) * 8  ) ) & 0xF );
  	}
  	return str;
}

function binl2b64( binarray ){
 	var tab = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
  	var str = "";
  	for ( var i = 0 ; i < binarray.length * 4 ; i += 3 ) {
    	var triplet = ( ( ( binarray[ i   >> 2 ] >> 8 * ( i   %4 ) ) & 0xFF ) << 16 )
                	| ( ( ( binarray[ i + 1 >> 2 ] >> 8 * ( ( i + 1 )%4 ) ) & 0xFF ) << 8 )
                	| ( ( binarray[ i + 2 >> 2 ] >> 8 * ( ( i + 2 )%4 ) ) & 0xFF );
    for ( var j = 0 ; j < 4; j++ ) {
		if ( i * 8 + j * 6 > binarray.length * 32 )
			str += b64pad;
      	else 
			str += tab.charAt( ( triplet >> 6 * ( 3 - j ) ) & 0x3F );
    	}
  	}
  	return str;
}
/* md5加密 END */














/* 提示窗口 (警告) BEGIN */
$.fn[ 'RemindWoken' ] = function ( str ) {
	if ( !$( this ).attr( 'class' ) && !$( this ).attr( 'id' ))
		return 0;
	var n = noty({
		text        : str,
		type        : 'warning',
		dismissQueue: true,
		layout      : 'bottomCenter',
		theme       : 'defaultTheme',
		timeout		: 5000,
		maxVisible	: 10,
		animation	: {
			open	: 'animated flipInX',
			close	: 'animated flipOutX', 
			easing	: 'swing',
			speed	: 500
		}
	});
}
/* 提示窗口 (警告) END */









/* 提示窗口 (错误) BEGIN */
$.fn[ 'RemindWokenError' ] = function ( str ) {
	if ( !$( this ).attr( 'class' ) && !$( this ).attr( 'id' ))
		return 0;
	var n = noty({
		text        : str,
		type        : 'error',
		dismissQueue: true,
		layout      : 'bottomCenter',
		theme       : 'defaultTheme',
		timeout		: 5000,
		maxVisible	: 10,
		animation	: {
			open	: 'animated flipInX',
			close	: 'animated flipOutX', 
			easing	: 'swing',
			speed	: 500
		}
	});
}
/* 提示窗口 (错误) END */





/* 提示窗口 (成功) BEGIN */
$.fn[ 'RemindWokenSuccess' ] = function ( str ) {
	if ( !$( this ).attr( 'class' ) && !$( this ).attr( 'id' ))
		return 0;
	var n = noty({
		text        : str,
		type        : 'success',
		dismissQueue: true,
		layout      : 'bottomCenter',
		theme       : 'defaultTheme',
		timeout		: 5000,
		maxVisible	: 10,
		animation	: {
			open	: 'animated flipInX',
			close	: 'animated flipOutX', 
			easing	: 'swing',
			speed	: 500
		}
	});
}
/* 提示窗口 (成功) END */





/* 提示窗口 (选择) BEGIN */
$.fn[ 'RemindWokenSelect' ] = function ( options ) {
	if ( !$( this ).attr( 'class' ) && !$( this ).attr( 'id' ))
		return 0;
	var settings = {
			title : '',
			istrue : function () {
			},
			isfalse : function () {
			
			}
		},
		settings = $.extend( settings , options ),
		n = noty({
			text        : settings.title,
			type        : 'confirm',
			dismissQueue: true,
			layout      : 'bottomCenter',
			theme       : 'defaultTheme',
			maxVisible	: 10,
			animation	: {
				open	: 'animated flipInX',
				close	: 'animated flipOutX', 
				easing	: 'swing',
				speed	: 500
			},
			buttons: [
				{	
					addClass : 'btn btn-primary' , text : '确定' , onClick : function( $noty ) {
						$noty.close();
						settings.istrue( $noty );
					}
				},
				{ 
					addClass : 'btn btn-danger' , text : '取消' , onClick : function( $noty ) {
						$noty.close();
						settings.isfalse( $noty );
					}
				}
			]
		});
}
/* 提示窗口 (选择) END */








/* 发生请求错误 BEGIN */
$.fn[ 'ErrorTarget' ] = function ( str ) {
	if ( !$( this ).attr( 'class' ) && !$( this ).attr( 'id' ))
		return 0;
	if ( str != 'success' ) {
		$( 'body' ).RemindWokenError( '发生错误' );
	}
}
/* 发生请求错误 END */






















/* 页面布局 BEGIN */
$.fn[ 'PageLayout' ] = function () { 
	doc.on( 'click' , function () {
		$( window.parent.document ).find( '.open' ).removeClass( 'open' );
		$( window.parent.document ).find( '.el-btn-ul' ).remove();
	} );
	var self = $( this ),
		settings = {
		},
		exps = {
			left : function ( options ) {
				/* 左侧浮动 宽度可设置100% */
				if ( !self.attr( 'class' ) && !self.attr( 'id' ))
					return 0;
				var leftstyle = {
						width : ''
					};
				leftstyle = $.extend( leftstyle , options );
				self.addClass( 'ui-left' );
				var window_h = $( window ).height(),
					header_h = $( '.header' ).height();
				self.css( 'height' , window_h - header_h  + 'px' );
				if ( leftstyle.width ) {
					self.css( 'width' , leftstyle.width );
				}
				$( window ).resize( function () {
					self.css( 'height' , $( window ).height() - header_h + 'px' );
					self.css( 'width' , parseInt( $( window ).width() * leftstyle.width ) + 'px' );
				} );	
			},
			top : function () {
				/* 顶部样式 没什么用 */
				self.addClass( 'ui-top' );
			},
			bottom : function () {
				/* 顶部样式 没什么用 */
				self.addClass( 'ui-bottom' );
			},
			right : function () {
				/* 右侧浮动 没什么用处 */
				if ( !$( this ).attr( 'class' ) )
					return 0;
				self.addClass( 'ui-right' );
				var window_h = $( window ).height(),
					header_h = $( '.header' ).height();
				self.css( 'height' , window_h - header_h + 'px' );
				$( window ).resize( function () {
					self.css( 'height' , $( window ).height() - header_h + 'px' );
				} );
			},
			middle : function () {
				/* 居中 */
				self.addClass( 'ui-middle' );
			}
		}
	return exps;
}
/* 页面布局 END */






/* table 封装 BEGIN */
/*  解决easyui本身换行bug
	formatter : function ( value , rowData , rowIndex ) {
		return '<div style="width:95px;display:block;word-break: break-all;word-wrap: break-word">' +  + '</div>';
	}
*/
$.fn[ 'table' ] = function () {
	var self = $( this ),
		selfp = self.parent(),
		tablename = self.attr( 'data-tablename' ),
		settings = {
			data : {},
			pagination : true,				/* 分页工具条 */
			rownumbers : true,				/*  */
			checkbox : false,				/* 复选框 */
			fitColumns : true,				/* 自适应大小 */
			striped : true,					/*  */
			resizable : true,				/* 大小自适应 */
			height : self.parent().height(),	/* 表格初始化的高度  最好是外容器的高度 否则大小改变时会不美观*/
			width : self.parent().width(),		/* 表格初始化的宽度 最好是外容器的宽度 否则大小改变时会不美观 */
			columns : [],					/* 列对象 */
			tableintab : '',				/* tab框对象 */
			fnbefore : '',					/* 表格加载前调用的方法 没什么用处 */
			fnafter : function () {	
				
			},								/* 表格加载后调用的方法 */
			numberwidth : function(){
				var rows =selfp.find('div.datagrid-cell-rownumber');
	        	if(rows.length > 0){
	        		var len = rows[rows.length-1].innerHTML.length;
	        		if(len > 4){
	        			selfp.find(".datagrid-view1 table.datagrid-htable").width(31+(len-4)*6);
	        			selfp.find(".datagrid-view1 table.datagrid-btable").width(31+(len-4)*6);
	        		}else{
	        			selfp.find(".datagrid-view1 table.datagrid-htable").width(31);
	        			selfp.find(".datagrid-view1 table.datagrid-btable").width(31);
	        		}
	        	}
			},                              /* 修改序号列宽度*/
			toolbar : '',					/* 工具按钮 现在没用处 */
			singleSelect : false,			/* 只允许选中一行 */
			selectOnCheck : false,			/*  */
			loadMsg : '数据加载中',			/* 初始化时显示的文字 */
			pageSize : 50,					/* 每页显示条数 */
			pageList: [ 50 , 100 , 150 ],	/* 可选择的每页显示条数 */
			url : '',						/* 异步加载时请求的方法 */
			type : 'GET',					/* 请求的类型 */
			fnquery : function ( pageNumber , pageSize ){
				
				
			},	/* 向后台请求时获取参数的方法 */
			paramname : '',					/* 从后台请求到数据的对象名 */
			nowrap : false,					/* 是否换行 */
			remoteSort : false,				/* 列内排序 */
			hiddencolumn : true,			/* 隐藏显示列的功能 */
			fnajaxafter:function (){
				
			},		/*  */
			fnajaxerror : '',				/*  */
			onClickRow : function () {
			
			},								/* 点击行时触发的方法 */
			column_hidepanel_width : 565,	/* 隐藏显示列层的宽度 */
			column_hidepanel_height : 445,	/* 隐藏显示列层的高度 */
			view : '',						/* 表格视图 */
			detailFormatter : function () {
			
			},								/*  */
			_rownumbersdata : '',
			sortbybg : false,
			onExpandRow : function () {
			
			},							/* 展开表格的每行 */
			tabledata : '',
			fntarget : function () {
				window.parent.document.location.replace( _url );
			},
			targethtmlclass : 'HZ-LOGIN'
		};
		var exps = {
			init : function ( options ) {
				if ( !self.attr( 'class' ) && !self.attr( 'id' ))
					return 0;
				var selfparent = self.parent();
				settings = $.extend( settings , options );
				self.data( 'settings' , settings );
				if ( settings.hiddencolumn ) {
					if( tablename != null && tablename != undefined && tablename != ''){
					  $( 'body' ).reajax({
							url : 'columnset/getInfo.do',
							type : 'GET',
							data : { tableName : tablename},
							targethtmlclass : settings.targethtmlclass,
							fnafter : function ( data ) {
								if( data.content != null && data.content.columns != undefined && data.content.columns != ''){
									var newcolumn = JSON.parse( data.content.columns ),
										newcolumns = new Array();
									var dbsetCol= new Array();
									var locsetCol= new Array();
									
									for( var i01=0;i01<newcolumn[ 0 ].length;i01++){
										dbsetCol[newcolumn[ 0 ][ i01 ].field]=newcolumn[ 0 ][ i01 ];
									}
									
									for(var i01=0;i01<settings.columns[ 0 ].length;i01++){
										locsetCol[settings.columns[ 0 ][i01 ].field]=settings.columns[ 0 ][i01 ];
										if(dbsetCol[settings.columns[ 0 ][i01 ].field]==undefined){
											dbsetCol[settings.columns[ 0 ][i01 ].field]=settings.columns[ 0 ][i01 ];
											newcolumn[0][newcolumn[0].length]=settings.columns[ 0 ][i01 ];
										}
									}
									
									for(var i01=0;i01<newcolumn[ 0 ].length;i01++){
										
										var colitem=dbsetCol[newcolumn[ 0 ][ i01 ].field];
										if(colitem!=undefined){
											if(locsetCol[colitem.field]!=undefined){
												locsetCol[colitem.field].hidden=colitem.hidden;
												newcolumns[newcolumns.length]=locsetCol[colitem.field];
											}
										}
									}

									settings.columns = [ newcolumns ];
									exps.createtable( selfparent );
								} else {
									exps.createtable( selfparent );
								}
							},
							inerror:function(){
								exps.createtable( selfparent );
							},
							fntarget : function () {
								settings.fntarget();
							},
						});
					}else{
						exps.createtable( selfparent );
					}
				
				} else {
					exps.createtable( selfparent );
				}
				
			},
			tableresize : function () {
				$( window ).resize( function () {
					self.datagrid( 'resize' , {
						width : selfparent.width(),
						height : selfparent.height()
					} );
				} );
			},
			getoptions:function(){
				/* 获取参数 */
				return self.data( 'settings' );
			},
			hiddencolumn : function ( container ) {
				/* 隐藏显示列 */
				container.find( '.datagrid-header-rownumber' ).html( '<i class="icon-th-list" style="margin-top:3px;display:block"></i>' );
				container.find( '.datagrid-header-rownumber' ).on( 'click' , function () {
					var th_len_arr = new Array(),
						th_sortlen_arr = new Array(),
						tablecolumn = settings.columns[ 0 ],
						newcolumn = {};
					for ( var c = 0 ; c < tablecolumn.length ; c++ ) {
						var checkbox = tablecolumn[ c ].hidden ? 'icon-check-empty' : 'icon-check',
							nocheckth = tablecolumn[ c ].hidden ? 'nocheckedth' : 'actives';
						var _field = tablecolumn[ c ].field;
						newcolumn[ _field ] = tablecolumn[ c ];
						th_len_arr.push(
							'<li class="' + tablecolumn[ c ].field.replace( '.' , '-' ) + ' ' + nocheckth + '">' +
								'<span class="hide-tablethcheck"><i class="' + checkbox + '"></i></span>' +
								'<span class="hide-tablethcheck-en">' + tablecolumn[ c ].field + '</span>' +
								'<span class="hide-tablethcheck-cn">' + tablecolumn[ c ].title + '</span>' +
							'</li>'
						);
						if ( !tablecolumn[ c ].hidden ) {
							th_sortlen_arr.push( 
								'<li class="' + tablecolumn[ c ].field.replace( '.' , '-' ) + '" >' +
									'<span class="show-tablethcheck"><i class="icon-remove"></i></span>' +
									'<span class="show-tablethcheck-en">' + tablecolumn[ c ].field + '</span>' +
									'<span class="show-tablethcheck-cn">' + tablecolumn[ c ].title + '</span>' +
								'</li>'
							);
						}
					}
					container.epanelhistory().init({
						title : '显示/隐藏表格字段（可排序）',
						_height : settings.column_hidepanel_height,
						_width : settings.column_hidepanel_width,
						data : '<div class="hideanshowtablecolumn">' +
									'<div style="width:100%;height:100%;position:relative;">' +
										'<div class="table-edit-wrap-container-content">' +
											'<ul>' +
												th_len_arr.join( '' ) +
											'</ul>' +
										'</div>' +
										'<div class="table-edit-wrap-container-content-B">' +
											'<div class="table-edit-wrap-container-content-B-title">以下为表格的显示列和顺序</div>' +
											'<ul>' +
												th_sortlen_arr.join( '' ) +
											'</ul>' +
										'</div>' +
									'</div>' +
								'</div>' +
								'<div style="position:absolute;width:100%;height:45px;bottom:0;">' +
									'<div class="mybutton purple table-edit-wrap-button ctr-style-margin-auto" style="margin-top:10px"><i class="icon-ok-circle"></i>确定</div>' +
								'</div>',
						_class : 'tablethcheck tableedit'
					});
					$( '.table-edit-wrap-container-content li' ).on( 'click' , function () {
						var _vels = $( this );
						if ( _vels.hasClass( 'actives' ) ) {
							_vels.removeClass( 'actives' );
							_vels.addClass( 'nocheckedth' );
							_vels.find( '.hide-tablethcheck' ).html( '<i class="icon-check-empty"></i>' );
							$( '.table-edit-wrap-container-content-B ul' ).find( '.' + _vels.find( '.hide-tablethcheck-en' ).text().replace( '.' , '-' ) ).remove();
						} else {
							_vels.removeClass( 'nocheckedth' );
							_vels.addClass( 'actives' );
							_vels.find( '.hide-tablethcheck' ).html( '<i class="icon-check"></i>' );
							$( '.table-edit-wrap-container-content-B ul' ).append( 
								'<li class="' + _vels.find( '.hide-tablethcheck-en' ).text().replace( '.' , '-' ) + '">' +
									'<span class="show-tablethcheck"><i class="icon-remove"></i></span>' +
									'<span class="show-tablethcheck-en">' + _vels.find( '.hide-tablethcheck-en' ).text() + '</span>' +
									'<span class="show-tablethcheck-cn">' + _vels.find( '.hide-tablethcheck-cn' ).text() + '</span>' +
								'</li>'
							);
						}
					} );
					doc.on( 'click' , '.show-tablethcheck' , function () {
						var _vels = $( this ); 
						$( '.table-edit-wrap-container-content ul' ).find( '.' + _vels.parent().find( '.show-tablethcheck-en' ).text().replace( '.' , '-' ) ).removeClass( 'actives' );
						$( '.table-edit-wrap-container-content ul' ).find( '.' + _vels.parent().find( '.show-tablethcheck-en' ).text().replace( '.' , '-' ) ).find( '.hide-tablethcheck' ).html( '<i class="icon-check-empty"></i>' );
						_vels.parent().remove();
					} );
					$( '.tablethcheck .table-edit-wrap-button' ).on( 'click' , function () {
						if ( $( '.table-edit-wrap-container-content-B li' ).length < 1 ) {
							$( 'body' ).RemindWoken( '选择需要显示的列' );
							return;
						}
						var thnosort = $( '.table-edit-wrap-container-content li' ),
							columnheadershow = [],
							columnheaderhide = [];
						for ( var c = 0 ; c < thnosort.length ; c++ ) {
							if ( thnosort.eq( c ).hasClass( 'actives' ) ) {
								
							} else {
								newcolumn[ thnosort.eq( c ).find( '.hide-tablethcheck-en' ).text() ].hidden = true;
								columnheaderhide.push(
									newcolumn[ thnosort.eq( c ).find( '.hide-tablethcheck-en' ).text() ]
								);
							}
						}
						var thsort = $( '.table-edit-wrap-container-content-B li' );
						for ( var c = 0 ; c < thsort.length ; c++ ) {
							newcolumn[ thsort.eq( c ).find( '.show-tablethcheck-en' ).text() ].hidden = false;
							columnheadershow.push(
								newcolumn[ thsort.eq( c ).find( '.show-tablethcheck-en' ).text() ]
							);	
						}
						/* 传到后台的参数 */
						var tablecolumn = [ columnheadershow.concat( columnheaderhide ) ];
						
						$( 'body' ).reajax({
							url : 'columnset/updateInfo.do',
							type : 'POST',
							data : { tableName : tablename , columnSet : JSON.stringify( tablecolumn) },
							fntarget : function () {
								settings.fntarget()
							},
							targethtmlclass : settings.targethtmlclass,
							fnafter : function () {
								selfp.find( '.nosource-title' ).remove();
								/* 请求成功后执行的方法 */
								exps.init( {
									columns : tablecolumn
								} );
								container.epanelhistory().removepanel();
								/*$( '.table-edit-wrap' ).remove();*/
								$( 'body' ).RemindWokenSuccess( '操作成功' );
							},
							fnsource : function ( data ) {
								selfp.find( '.nosource-title' ).remove();
								selfp.find( '.datagrid-body' ).eq( 1 ).append( '<div class="nosource-title" style="letter-spacing:1px;font-size:22px;color:#aaa;text-align:center;height:100px;line-height:100px;width:100%;">' + data.message.notice + '</div>' );
							},
							inerror : function ( edata ) {
								return ( settings.fnajaxerror == '' ? '' : settings.fnajaxerror( edata ) );
							}
						});
					} );
				} );
			},
			refresh : function ( selfparent ) {
				
				/* 刷新表 */
				selfp.append( 
					'<div class="nosource-title aaa" style="position:absolute;top:200px;height:100px;width:' + selfp.width() + 'px;z-index:999;">' +
						'<div style="width:195px;height75px;background:rgba(255,255,255,0.9);box-shadow:0 0 10px #888;margin:0 auto;letter-spacing:1px;font-size:22px;color:#aaa;text-align:center;line-height:70px;"><i class="icon-spinner icon-spin"></i> 数据加载中</div>' +
					'</div>' 
				);
				settings = exps.getoptions();
				if ( settings.url != undefined && settings.url != '' && settings.url.length > 0 ) {
					settings.fnafter( selfparent );
					settings.numberwidth();
					if ( settings.tableintab == '' ) {
						self.datagrid( 'resize' , {
							width : settings.width
						} );
					}
					$( 'body' ).reajax( {
						type : settings.type,
						url : settings.url,
						data : settings.fnquery( 1 , exps.pagesize() ),
						fntarget : function () {
							settings.fntarget()
						},
						targethtmlclass : settings.targethtmlclass,
						fnafter : function ( data ) {
							settings.tabledata = data;
							selfp.find( '.nosource-title' ).remove();
							
							self.datagrid( 'loadData' , {
								total : ( data.content[ settings.paramname ][ 'total' ] == undefined ? 0 : data.content[ settings.paramname ][ 'total' ] ),
								rows : ( data.content[ settings.paramname ][ 'rows' ] == undefined ? data.content[ settings.paramname ] : data.content[ settings.paramname ][ 'rows' ] )
							} );
							
							exps.onSelectPage();
							settings._rownumberdata = data.content[ settings.paramname ][ 'rows' ];
							exps._rownumber();
							selfp.find( '.nosource-title' ).remove();
							var _ekkk = data.content[ settings.paramname ][ 'total' ] == undefined ? 0 : data.content[ settings.paramname ][ 'total' ];
							if ( _ekkk == 0 && settings.pagination ) {
								selfp.find( '.datagrid-body' ).eq( 1 ).append( '<div class="nosource-title" style="letter-spacing:1px;font-size:22px;color:#aaa;text-align:center;height:100px;line-height:100px;width:100%;">无数据</div>' );
							}
							settings.fnafter( selfp );
							settings.numberwidth();
							if ( settings.tableintab == '' ) {
								self.datagrid( 'resize' , {
									width : settings.width
								} );
							}
							settings.fnajaxafter( data );
						},
						fnsource : function ( data ) {
							selfp.find( '.nosource-title' ).remove();
							var rowlenth = ( self.datagrid( 'getRows' ).length - 1 );
							for ( var c = 0 ; c < rowlenth ; c++ ) {
								self.datagrid( 'deleteRow' , 0 );
							}
							selfp.find( "tr[datagrid-row-index='0']" ).css({ 'visibility' : 'hidden' });
							exps.onSelectPage();
							settings.fnafter( selfp );
							settings.numberwidth();
							if ( settings.tableintab == '' ) {
								self.datagrid( 'resize' , {
									width : settings.width
								} );
							}
							selfp.find( '.datagrid-body' ).eq( 1 ).append( '<div class="nosource-title" style="letter-spacing:1px;font-size:22px;color:#aaa;text-align:center;height:100px;line-height:100px;width:100%;">' + data.message.notice + '</div>' );
							
						},
						inerror : function ( edata ) {
							return ( settings.fnajaxerror == '' ? '' : settings.fnajaxerror( edata ) );
						}
					} );
				}
			},
			_rownumber : function () {
				var _rownumber = selfp.find( 'div.datagrid-cell-rownumber' ),
					_pageindex = ( exps.getpageindex() == undefined ? 0 : exps.getpageindex() - 1 ),
					_pagesize = ( exps.pagesize() == undefined ? 0 : exps.pagesize() );
				if ( settings._rownumberdata != undefined ) {
					for ( var c = 0 ; c < _rownumber.length ; c++ ) {
						if ( settings._rownumberdata[ c ]._rownumbers != undefined ) {
							_rownumber.eq( c ).html( exps.getRows()[ c ]._rownumbers );
						} else {
							_rownumber.eq( c ).html( _pageindex * _pagesize + c + 1 );
							/*settings._rownumberdata[ c ][ '_rownumbers' ] = _pageindex * _pagesize + c + 1;*/
						}
					}
				}
			},
			tablesortable : function () {
				var ccc = self.data( 'settings' );
				return ccc.fnquery( exps.getpageindex() , exps.pagesize() );
			},
			tablerefresh : function ( _aaaa ) {
				/* 刷新表 */
				selfp.append( 
					'<div class="nosource-title aaaa" style="position:absolute;top:200px;height:100px;width:100%;z-index:999;">' +
						'<div style="width:195px;height75px;background:rgba(255,255,255,0.9);box-shadow:0 0 10px #888;margin:0 auto;letter-spacing:1px;font-size:22px;color:#aaa;text-align:center;line-height:70px;"><i class="icon-spinner icon-spin"></i> 数据加载中</div>' +
					'</div>' 
				);
				var cxxg = exps.getpageindex(),
					cxxp = exps.pagesize();
				/* 按照自己定义的参数刷新表 */
				settings = exps.getoptions();
				var xxx = {
					fnquery : function ( pageNumber , pageSize ) {
						/*settings.fnquery( cxxg , cxxp )*/
					}
			  	};
				if ( _aaaa != undefined ) {
					xxx = $.extend( xxx , _aaaa );
					settings = $.extend( settings , xxx );
					self.data( 'settings' , settings );
				}
/*				var start = new Date().getTime();//起始时间
				var _length = exps.getRows().length;
				for ( var c = 0 ; c < _length ; c++ ) {
					self.datagrid( 'deleteRow' , 0 );	
				}
				var end = new Date().getTime();//接受时间
        		*/
				
				
				$( 'body' ).reajax( {
					type : settings.type,
					url : settings.url,
					data : ( xxx.fnquery( cxxg , cxxp ) == undefined ? settings.fnquery( cxxg , cxxp ) : xxx.fnquery( cxxg , cxxp ) ),
					fntarget : function () {
						settings.fntarget()
					},
					targethtmlclass : settings.targethtmlclass,
					fnafter : function ( data ) {
						settings.tabledata = data;
						selfp.find( '.nosource-title' ).remove();
						self.datagrid( 'loadData' , {
							total : ( data.content[ settings.paramname ][ 'total' ] == undefined ? 0 : data.content[ settings.paramname ][ 'total' ] ),
							rows : ( data.content[ settings.paramname ][ 'rows' ] == undefined ? data.content[ settings.paramname ] : data.content[ settings.paramname ][ 'rows' ] )
						} );
						exps.onSelectPage();
						settings._rownumberdata = data.content[ settings.paramname ][ 'rows' ];
						exps._rownumber();
						selfp.find( '.nosource-title' ).remove();
						var _ekkk = data.content[ settings.paramname ][ 'total' ] == undefined ? 0 : data.content[ settings.paramname ][ 'total' ];
						if ( _ekkk == 0 ) {
							selfp.find( '.datagrid-body' ).eq( 1 ).append( '<div class="nosource-title" style="letter-spacing:1px;font-size:22px;color:#aaa;text-align:center;height:100px;line-height:100px;width:100%;">无数据</div>' );
						}
						settings.fnafter( selfp );
						settings.numberwidth();
						if ( settings.tableintab == '' ) {
							self.datagrid( 'resize' , {
								width : settings.width
							} );
						}
						settings.fnajaxafter( data );
					},
					fnsource : function ( data ) {
						selfp.find( '.nosource-title' ).remove();
						var rowlenth = ( self.datagrid( 'getRows' ).length - 1 );
						for ( var c = 0 ; c < rowlenth ; c++ ) {
							self.datagrid( 'deleteRow' , 0 );
						}
						selfp.find( "tr[datagrid-row-index='0']" ).css({ 'visibility' : 'hidden' });
						exps.onSelectPage();
						settings.fnafter( selfp );
						settings.numberwidth();
						if ( settings.tableintab == '' ) {
							self.datagrid( 'resize' , {
								width : settings.width
							} );
						}
						selfp.find( '.datagrid-body' ).eq( 1 ).append( '<div class="nosource-title" style="letter-spacing:1px;font-size:22px;color:#aaa;text-align:center;height:100px;line-height:100px;width:100%;">' + data.message.notice + '</div>' );
						
					},
					inerror : function ( edata ) {
						return ( settings.fnajaxerror == '' ? '' : settings.fnajaxerror( edata ) );
					}
		    	} );
			},
			getpageindex : function () {
				/* 获取当前是那一页 */
				return selfp.parent().find( '.pagination-num' ).val();			
			},
			
			sortbybg : function ( sorts , order ) {
				if ( settings.remoteSort ) {
					var bbbb = settings.fnquery( exps.getpageindex() , exps.pagesize() );
					bbbb.pageNumber = exps.getpageindex();
					exps.tablerefresh( {
						fnquery : function ( pageNumber , pageSize ) {
							var aaaa = {};
							aaaa = bbbb;
							aaaa[ 'sortable' ] = sorts;
							aaaa[ 'order' ] = order;
							return aaaa;
						}
					} );
				}
			},
			reload : function ( data ) {
				/* 重新加载数据 */
				var refreshsettings = {
					total : '',
					rows : ''
				};
				refreshsettings = $.extend( refreshsettings , data );
				self.datagrid( 'loadData' , refreshsettings );
				
			},
			createtable : function ( selfparent ) {
				/* 创建表 */
				if ( settings.view == '' ) {
					self.datagrid({
						pagination : settings.pagination,
						rownumbers : settings.rownumbers,
						checkbox : settings.checkbox,
						fitColumns : settings.fitColumns,
						striped : settings.striped,
						showFooter:true,
						/*scrollbarSize : settings.scrollbarSize,*/
						resizable : settings.resizable,
						columns : settings.columns,
						height : settings.height,
						/*width : settings.width,*/
						singleSelect : settings.singleSelect,
						nowrap : settings.nowrap,
						remoteSort : settings.remoteSort,
						selectOnCheck : settings.selectOnCheck,
						loadMsg : settings.loadMsg,
						pageSize : settings.pageSize,
						pageList : settings.pageList,
						onClickRow : function ( rowIndex , rowData ) {
							settings.onClickRow( rowIndex , rowData );
						},
						onSortColumn : function ( sorts , order ) {
							exps._rownumber();
							exps.sortbybg( sorts , order );
						},
						detailFormatter : function ( index , row ) {
							return settings.detailFormatter( index , row );
						}
					});
				} else {
					self.datagrid({
						pagination : settings.pagination,
						rownumbers : settings.rownumbers,
						checkbox : settings.checkbox,
						fitColumns : settings.fitColumns,
						striped : settings.striped,
						showFooter:true,
						/*scrollbarSize : settings.scrollbarSize,*/
						resizable : settings.resizable,
						columns : settings.columns,
						height : settings.height,
						nowrap : settings.nowrap,
						remoteSort : settings.remoteSort,
						/*width : settings.width,*/
						singleSelect : settings.singleSelect,
						selectOnCheck : settings.selectOnCheck,
						loadMsg : settings.loadMsg,
						pageSize : settings.pageSize,
						pageList : settings.pageList,
						onClickRow : function ( rowIndex , rowData ) {
							settings.onClickRow( rowIndex , rowData );
						},
						view : settings.view,
						onSortColumn : function ( sorts , order ) {
							exps._rownumber();
							exps.sortbybg( sorts , order );
						},
						detailFormatter : function ( index , row ) {
							return settings.detailFormatter( index , row );
						}
					});
				}
				$( window ).resize( function () {
					if ( settings.tableintab != '' ) {
						self.datagrid( 'resize' , {
							width : settings.tableintab.width(),
						} );
					} else {
						self.datagrid( 'resize' , {
							width : selfparent.width(),
							height : selfparent.height()
						} );	
					}
				} );
				if ( settings.hiddencolumn ) {
					exps.hiddencolumn( selfparent );
				}
				
				exps.refresh( selfparent );
			},
			tabledatas : function () {
				return settings.tabledata;
			},
			datainit : function ( data ) {
				/* 将toolbar的内容放入列的对象 */
				settings=exps.getoptions();
				var datas = data;
				if ( settings.toolbar != '' ) {
					for ( var c = 0 ; c < data.content[ settings.paramname ][ 'rows' ].length ; c++ ) {
						data.content[ settings.paramname ][ 'rows' ][ c ][ 'toolbar' ] = settings.toolbar;
					}
					datas = data;
				}
				return datas;
			},
			getpager : function () {
				/* 获取当前页 */
				return self.datagrid( 'getPager' );
			},
			onSelectPage : function () {
				
				/* 选中某一页时触发 */
				settings = exps.getoptions();
				exps.getpager().pagination( {
					onSelectPage : function ( pageNumber , pageSize ) {
						/* 刷新表 */
						selfp.append( 
							'<div class="nosource-title  aa" style="position:absolute;top:200px;height:100px;width:100%;z-index:999;">' +
								'<div style="width:195px;height75px;background:rgba(255,255,255,0.9);box-shadow:0 0 10px #888;margin:0 auto;letter-spacing:1px;font-size:22px;color:#aaa;text-align:left;line-height:70px;"><i class="icon-spinner icon-spin"></i> 数据加载中</div>' +
							'</div>' 
						);
						settings.fnquery( pageNumber , exps.pagesize() ).pageNumber = pageNumber
						$( 'body' ).reajax( {
							type : settings.type,
							url : settings.url,
							fntarget : function () {
								settings.fntarget()
							},
							targethtmlclass : settings.targethtmlclass,
							data : settings.fnquery( pageNumber , exps.pagesize() ),
							fnafter : function ( data ) {
								settings.tabledata = data;
								selfp.find( '.nosource-title' ).remove();
								var datac = exps.datainit( data );
								exps.reload( {
									total : ( datac.content[ settings.paramname ][ 'total' ] == undefined ? 0 : datac.content[ settings.paramname ][ 'total' ] ),
									rows : ( datac.content[ settings.paramname ][ 'rows' ] == undefined ? datac.content[ settings.paramname ] : datac.content[ settings.paramname ][ 'rows' ] )
								} );
								
								settings._rownumberdata = datac.content[ settings.paramname ][ 'rows' ];
								exps._rownumber();
								selfp.find( '.nosource-title' ).remove();
								var _ekkk = datac.content[ settings.paramname ][ 'total' ] == undefined ? 0 : datac.content[ settings.paramname ][ 'total' ];
								if ( _ekkk == 0 ) {
									selfp.find( '.datagrid-body' ).eq( 1 ).append( '<div class="nosource-title" style="letter-spacing:1px;font-size:22px;color:#aaa;text-align:center;height:100px;line-height:100px;width:100%;">无数据</div>' );
								}
								settings.fnafter( selfp );
								settings.numberwidth();
								if ( settings.tableintab == '' ) {
									self.datagrid( 'resize' , {
										width : settings.width
									} );
								}
							},
							fnsource : function ( data ) {
								selfp.find( '.nosource-title' ).remove();
								var rowlenth = ( self.datagrid( 'getRows' ).length - 1 );
								for ( var c = 0 ; c < rowlenth ; c++ ) {
									self.datagrid( 'deleteRow' , 0 );
								}
								selfp.find( "tr[datagrid-row-index='0']" ).css({ 'visibility' : 'hidden' });
								exps.onSelectPage();
								settings.fnafter( selfp );
								settings.numberwidth();
								if ( settings.tableintab == '' ) {
									self.datagrid( 'resize' , {
										width : settings.width
									} );
								}
								selfp.find( '.datagrid-body' ).eq( 1 ).append( '<div class="nosource-title" style="letter-spacing:1px;font-size:22px;color:#aaa;text-align:center;height:100px;line-height:100px;width:100%;">' + data.message.notice + '</div>' );
							},
							inerror : function ( edata ) {
								return ( settings.fnajaxerror == '' ? '' : settings.fnajaxerror( edata ) );
							}
						} );
					}
				} );
			},
			pagesize : function () {
				/* 当前页的显示条数 */
				return  exps.getpager().find( '.pagination-page-list' ).val();
			},
			requestdata : function ( options ) {
				/*  */
				var request = {
					type : settings.type,
					url : settings.url
				};
				request = $.extend( request , options );
				$( 'body' ).reajax( {
					type : request.type,
					url : request.url,
					data : settings.fnquery( 1 , exps.pagesize() ),
					fntarget : function () {
						settings.fntarget()
					},
					targethtmlclass : settings.targethtmlclass,
					fnafter : function ( data ) {
						settings.tabledata = data;
						selfp.find( '.nosource-title' ).remove();
						exps.reload( {
							total : ( data.content[ settings.paramname ][ 'total' ] == undefined ? 0 : data.content[ settings.paramname ][ 'total' ] ),
							rows : ( data.content[ settings.paramname ][ 'rows' ] == undefined ? data.content[ settings.paramname ] : data.content[ settings.paramname ][ 'rows' ] )
						} );
						var _ekkk = data.content[ settings.paramname ][ 'total' ] == undefined ? 0 : data.content[ settings.paramname ][ 'total' ];
						if ( _ekkk == 0 ) {
							selfp.find( '.datagrid-body' ).eq( 1 ).append( '<div class="nosource-title" style="letter-spacing:1px;font-size:22px;color:#aaa;text-align:center;height:100px;line-height:100px;width:100%;">无数据</div>' );
						}
						exps.onSelectPage();
					},
					fnsource : function ( data ) {
						selfp.find( '.nosource-title' ).remove();
						var rowlenth = ( self.datagrid( 'getRows' ).length - 1 );
						for ( var c = 0 ; c < rowlenth ; c++ ) {
							self.datagrid( 'deleteRow' , 0 );
						}
						selfp.find( "tr[datagrid-row-index='0']" ).css({ 'visibility' : 'hidden' });
						exps.onSelectPage();
						settings.fnafter( selfp );
						settings.numberwidth();
						if ( settings.tableintab == '' ) {
							self.datagrid( 'resize' , {
								width : settings.width
							} );
						}
						selfp.find( '.datagrid-body' ).eq( 1 ).append( '<div class="nosource-title" style="letter-spacing:1px;font-size:22px;color:#aaa;text-align:center;height:100px;line-height:100px;width:100%;">' + data.message.notice + '</div>' );
					},
					inerror : function ( edata ) {
						return ( settings.fnajaxerror == '' ? '' : settings.fnajaxerror( edata ) );
					}
				} );
			},
			getRows : function () {
				/* 获取当页所有行 */
				return self.datagrid( 'getRows' );
			},
			getChecked : function () {
				/* 获取复选框选中的行 */
				return self.datagrid( 'getChecked' );
			},
			tableobject : function () {
				/* 表格对象 */
				return self;
			},
			expandRow : function ( index ) {
				/* 展开某一行 特性表格视图使用 */
				self.datagrid( 'expandRow' , index );
			},
			collapseRow : function ( index ) {
				/* 折叠某行 特定表格视图使用 */
				self.datagrid( 'collapseRow' , index );
			}
		}
	return exps;
}
/* table 封装 END */






/* tree 封装 BEGIN */
$.fn[ 'etree' ] = function () {
	var self = $( this ),
		settings = {
			lines : true,			/* 是否有辅助线 */
			data : {},				/* 后台取出的数据 */
			fnbefore : function () {
			
			},						/* 树加载前执行的方法，适用于异步加载 */
			fnafter : function () {
			
			},						/* 树加载后执行的方法，适用于异步加载 */			
			reload : true,			/* 重新加载树的数据 */
			expand : true,			/* 展开树的所有节点 */
			closed : true,			/* 折叠树的所有节点 */
			checkbox : false,		/* 复选框 */
			onlyLeafCheck:false,
			title : '组织',			/* 树的标题 */
			esearch : true,			/* 是否有搜索框 */
			output : false,			/* 导出 */
			_node : '',				/*  */
			onclicks : function () {
			
			},
			onclick : function ( node ) {
				settings._node = node;
				settings.onclicks( node );
			},						/* 当节点被点击时触发的方法 */
			toolbar : {
						edit : false,
						delete : false,
						add : false
			},						/* 节点工具按钮 */
			toolbarclick : {
				edit : function ( node ) {
				},
				delete : function ( node ) {
				},
				add : function ( node ) {
				}
			},						/* 节点工具按钮被点击时触发的方法 */
			toolbarcontent : '',
			markadd : {
				markadd_column : ''
			},						/* 控制某一节点字体变色 */
			node : {
				_pid : '',
				_id : '',
				_name : '',
				_markcode : '',		/* 助记码 */
				_countname : '',	/* 不传此字段名按照默认数量  传则按照此字段显示数量 */
                _nodetype:'',
				_attributes : []
			},						/* 节点对象 */
			openLevel:1,
			showtype:1021,
			checkall : false			/* 选中全部按钮 需配合复选框使用 */
		},
		exps = {
			init : function ( options ) {
				if ( !self.attr( 'class' ) && !self.attr( 'id' ))
					return 0;
				settings = $.extend( settings , options );
				var button = '',
					esearch = !settings.esearch ? '' : '<div class="manager-tree-select-wrap">' +
															'<div class="manager-tree-select">' +
																'<span class="manager-tree-select-i"><i class="icon-search"></i></span>' +
																'<input class="manager-tree-select-input" type="text" placeholder="助记码/汉字">' +
															'</div>' +
													   '</div>',
					toolbar = '';
				if ( settings.reload ) {
					button+= '<li class="tree-thermotics-refresh">' +
								'<a class="dropdown-toggle" data-toggle="dropdown">刷新</a>' +
							'</li>';
				}
				if ( settings.expand ) {
					button+= '<li class="tree-thermotics-deploy">' +
								'<a class="dropdown-toggle" data-toggle="dropdown">展开全部</a>' +
							'</li>';
				}
				if ( settings.closed ) {
					button+= '<li class="tree-thermotics-shrink">' +
								'<a class="dropdown-toggle" data-toggle="dropdown">折叠全部</a>' +
							'</li>';
				}
				if ( settings.output ) {
					button+= '<li class="tree-thermotics-output">' +
								'<a class="dropdown-toggle" data-toggle="dropdown">导出</a>' +
							'</li>';
				}
				if ( settings.checkall ) {
					button+= '<li class="tree-thermotics-checkall">' +
								'<a class="dropdown-toggle" data-toggle="dropdown">选中全部</a>' +
							'</li>';
				}
				self.append(
					'<div class="float-chart-title">' +
						'<div class="discription">' + settings.title + '<span class="conner"></span></div>' +
						'<div class="discription-tool">' +
							'<a class="dropdown-toggle" data-toggle="dropdown"><i class="icon-reorder"></i></a>' +
							'<ul class="dropdown-menu pull-right">' +
							button +
							'</ul>' +
						'</div>' +
					'</div>' +
					esearch +
					'<div class="manager-permission-wrap-wrap">' +
						'<div class="manager-permission-wrap">' +
							'<div class="manager-permission-tree-container"></div>' +
						'</div>' +
					'</div>'
				);
				self.find( '.manager-permission-wrap' ).niceScroll({
					styler : "fb" , 
					cursorcolor : "#f4f4f4" ,
					cursorwidth : '7' ,
					cursorborderradius : 0,
					cursorborder:'1px solid #999',
					background : '' ,
					cursoropacitymin  : 0.7,
					cursoropacitymax  : 1
				});
				if ( settings.toolbar.add ) {
					toolbar+= '<span class="tree-node-add">添加</span>';
				}
				if ( settings.toolbar.edit ) {
					toolbar+= '<span class="tree-node-edit">修改</span>';
				}
				if ( settings.toolbar.delete ) {
					toolbar+= '<span class="tree-node-delete">删除</span>';
				}
				if ( toolbar != '' ) {
					settings.toolbarcontent = '<span class="tree-toolbar">' +
													'<div class="tree-toolbar-icon"><i class="icon-tag"></i></div>' +
													'<div class="tree-toolbar-content">' +
														toolbar +
													'</div>' +
												'</span>';
				}
				self.find( '.manager-permission-tree-container' ).tree({
					data : new TreeChildsEx( settings.showtype).init( settings.data , settings.node,settings.openLevel),/*new TreeChilds( settings.data , settings.node ).init( 1 ),*/
					lines : settings.lines,
					onClick : function ( node ) {
						settings.onclick( node );
					},
					checkbox : settings.checkbox,
					onlyLeafCheck : settings.onlyLeafCheck,
					formatter : function( node ){
						var s = node.text;
						if ( settings.markadd.markadd_column != null ) {
							if ( node.attributes[ settings.markadd.markadd_column ] != null && node.attributes[ settings.markadd.markadd_column ] != '' ) {
								s = '<span style="color:#f00;">' + node.text + '</span>'
							}
						}
						
						if ( settings.node._countname == undefined || settings.node._countname == '' ) {
							if ( node.children ) {
								/*s += '&nbsp;<span style=\'color:#458fef\'>(' + node.children.length + ')</span>';*/
							}
						} else {
							s += '&nbsp;<span style=\'color:#458fef\'>(' + node.countname + ')</span>';
						}
						return s;
					}
				});
				exps.isearch();
				exps.toolbar();
				self.find( '.tree-thermotics-refresh' ).click( function () {
					exps.refresh( new TreeChilds( settings.data ).init( 1 ) );
				} );
				self.find( '.tree-thermotics-deploy' ).click( function () {
					exps.expand( 0 );
				} );
				self.find( '.tree-thermotics-shrink' ).click( function () {
					exps.closed( 0 );
				} );
				self.find( '.tree-thermotics-output' ).click( function () {
					exps.output( settings.output );
				} );
				self.find( '.tree-thermotics-checkall' ).click( function () {
					var _self = $( this );
					if ( _self.hasClass( 'check-actives' ) ) {
						_self.removeClass( 'check-actives' );
						exps.uncheckall();
					} else {
						_self.addClass( 'check-actives' );
						exps.checkall();
					}
				} );
				exps.toolbarclick();
				exps.toolbarclicks();
				/*exps.closed( 0 );*/
			},
			toolbarclicks : function () {
				/* 点击节点的工具按钮 */
				self.find( '.tree-node-add' ).on( 'click' , function () {
					settings.toolbarclick.add( settings._node );
					$( '.tree-toolbar-content' ).removeClass( 'active' );
				} );
				self.find( '.tree-node-edit' ).on( 'click' , function () {
					settings.toolbarclick.edit( settings._node );
					$( '.tree-toolbar-content' ).removeClass( 'active' );
				} );
				self.find( '.tree-node-delete' ).on( 'click' , function () {
					settings.toolbarclick.delete( settings._node );
					$( '.tree-toolbar-content' ).removeClass( 'active' );
				} );
			},
			update : function ( nodes , colors , attr ) {
				self.find( '.manager-permission-tree-container' ).tree( 'update' , {
					target: nodes.target,
					text: '<span style="color:' + colors + ';">' + nodes.text + '</span>',
					attributes : attr
				});
			},
			checkall : function () {
				/* 选中全部节点 和复选框配合使用 */
				var _rnode = exps.getRoot(),
					roots = self.find( '.manager-permission-tree-container' ).tree('getRoots');
				for ( var i = 0; i < roots.length; i++) {  
					var node = self.find( '.manager-permission-tree-container' ).tree( 'find' , roots[ i ].id ); 
					self.find( '.manager-permission-tree-container' ).tree( 'check' , node.target );
				}
				
						
					
			},
			uncheckall : function () {
				/* 反选全部节点 和复选框配合使用 */
				var _rnode = exps.getRoot(),
					roots = self.find( '.manager-permission-tree-container' ).tree('getRoots');
				for ( var i = 0; i < roots.length; i++) {  
					var node = self.find( '.manager-permission-tree-container' ).tree( 'find' , roots[ i ].id );  
					self.find( '.manager-permission-tree-container' ).tree('uncheck', node.target);  
				}
				
			},
			refresh : function ( data ) {
				/* 刷新树 */
				self.find( '.manager-permission-tree-container' ).children().remove();
				self.find( '.manager-permission-tree-container' ).tree({
					data : new TreeChilds( data , settings.node ).init( 1 ),
					lines : settings.lines,
					onclick : settings.onclick,
					formatter : function( node ){
						var s = node.text;
						if ( settings.node._countname == undefined || settings.node._countname == '' ) {
							/*if ( node.children ) {
								s += '&nbsp;<span style=\'color:#458fef\'>(' + node.children.length + ')</span>';
							}*/
						} else {
							s += '&nbsp;<span style=\'color:#458fef\'>(' + node.countname + ')</span>';
						}
						return s;
					}
				});
				exps.toolbar();
				exps.toolbarclick();
				exps.toolbarclicks();
			},
			isearch : function () {
				/* 搜索功能 */
				doc.on( 'focus' , '.manager-tree-select-input' , function () {
					self.find( '.manager-permission-tree-container' ).tree( 'select' , null );
					var _self = $( this ),
						_value = _self.val().replace( clearspace , '' );
					
					if ( _value != '' ) {
						for ( var c = 0 ; c < settings.data.length ; c++ ) {
							if ( settings.data[ c ][ settings.node._name ].indexOf( _value ) != -1 || ( settings.data[ c ][ settings.node._markcode ] != undefined && settings.data[ c ][ settings.node._markcode ].indexOf( _value ) != -1 ) ) {
								
								var _node = self.find( '.manager-permission-tree-container' ).tree( 'find' , settings.data[ c ][ settings.node._id ] );
								self.find( '.manager-permission-tree-container' ).tree( 'select' , _node.target );
								self.find( '.manager-permission-tree-container' ).tree( 'expandTo' , _node.target );
							}
						}
					} else {
						_self.parent().find( '.manager-tree-select-result' ).remove();
					}
				} );
				doc.on( 'input propertychange' , '.manager-tree-select-input' , function () {
					self.find( '.manager-permission-tree-container' ).tree( 'select' , null );
					var _self = $( this ),
						_value = _self.val().replace( clearspace , '' );
					
					if ( _value != '' ) {
						for ( var c = 0 ; c < settings.data.length ; c++ ) {
							if ( settings.data[ c ][ settings.node._name ].indexOf( _value ) != -1 || ( settings.data[ c ][ settings.node._markcode ] != undefined && settings.data[ c ][ settings.node._markcode ].indexOf( _value ) != -1 ) ) {
								
								var _node = self.find( '.manager-permission-tree-container' ).tree( 'find' , settings.data[ c ][ settings.node._id ] );
								self.find( '.manager-permission-tree-container' ).tree( 'select' , _node.target );
								self.find( '.manager-permission-tree-container' ).tree( 'expandTo' , _node.target );
							}
						}
					} else {
						_self.parent().find( '.manager-tree-select-result' ).remove();
					}
				} );
			},
			expand : function ( node ) {
				/* 展开所有节点 展开至某一节点 */
				if ( node == 0 ) {
					self.find( '.manager-permission-tree-container' ).tree( 'expandAll' );
				} else {
					
				}
			},
			getChecked : function ( node ) {
				/* 获取复选框勾选节点 */
				return self.find( '.manager-permission-tree-container' ).tree( 'getChecked' , node );
			},
			closed : function ( node ) {
				/* 折叠所有节点  折叠至某一节点 */
				if ( node == 0 ) {
					self.find( '.manager-permission-tree-container' ).tree( 'collapseAll' );
				} else {
					
				}
			},
			toolbarclick : function () {
				/* 点击节点的工具按钮 */
				self.find( '.tree-toolbar-icon' ).click( function () {
					var _self = $( this );
					self.find( '.tree-toolbar-icon' ).removeClass( 'active' );
					self.find( '.tree-toolbar-content' ).removeClass( 'active' );
					_self.addClass( 'active' );
					_self.parent().find( '.tree-toolbar-content' ).addClass( 'active' );
				} );
				doc.click( function ( e ) {
					if ( $( e.target ).attr( 'class' ) != 'tree-toolbar-icon' ) {
						self.find( '.tree-toolbar-icon' ).removeClass( 'active' );
						self.find( '.tree-toolbar-content' ).removeClass( 'active' );
					}
				} );
			},
			findnode : function ( node_id ) {
				var tree_node = self.find( '.manager-permission-tree-container' ).tree( 'find' , node_id );
				return tree_node;
			},
			output : function ( outurl ) {
				/* 树的节点导出 */
				location.href = _url + outurl + '?';
			},
			toolbar : function () {
				/* 将工具按钮放入树节点中 */
				var treenode = self.find( '.tree-node' );
				for ( var c = 0 ; c < treenode.length ; c++ ) {
						treenode.eq( c ).append( settings.toolbarcontent );
				}
			},
			treeobject : function () {
				/* 返回树的对象 */
				return self.find( '.manager-permission-tree-container' );
			},
			setselect : function () {
				
			},
			scrollTo : function () {
				/* 滚动至某一节点 */
			},
			expandTo : function ( node ) {
				/* 展开至某一节点 */
				self.find( '.manager-permission-tree-container' ).tree( 'expandTo' , node );
			},
			getRoot : function () {
				/* 获取根节点 */
				return self.find( '.manager-permission-tree-container' ).tree( 'getRoot' );
			},
			nodeselect : function ( node ) {
				/* 选中某一节点 */
				self.find( '.manager-permission-tree-container' ).tree( 'select' , node.target );
			},
			getParent : function ( node ) {
				/* 获取当前节点的父节点 */
				return self.find( '.manager-permission-tree-container' ).tree( 'getParent' , node.target );
			},
			findnode : function ( str ) {
				return self.find( '.manager-permission-tree-container' ).tree( 'find' , str );
			},
			expandToid : function ( id ) {
				var _node = self.find( '.manager-permission-tree-container' ).tree( 'find' , id );
				self.find( '.manager-permission-tree-container' ).tree( 'select' , _node.target );
				self.find( '.manager-permission-tree-container' ).tree( 'expandTo' , _node.target )
			},
			find : function ( data ) {
				/* 选中和展开至某一节点 */
				for ( var c = 0 ; c < data.length ; c++ ) {
					var _node = self.find( '.manager-permission-tree-container' ).tree( 'find' , data[ c ][ settings.node._id ] );
					self.find( '.manager-permission-tree-container' ).tree( 'select' , _node.target );
					self.find( '.manager-permission-tree-container' ).tree( 'expandTo' , _node.target )
				}
			}
		}
		
	return exps;
}
function TreeChilds( arr , node ) {
	this.array = arr || [];
	this.node = node;
	this.groups = {};
}
TreeChilds.prototype = {
	init : function ( allparent ) {
		this.group();
		var aee;
		/* 以下方法是用来找到根节点 还有待改进 */
		/*for ( var k = 0 ; k < this.array.length ; k++ ) {
			var cy = 0;
			for ( var q = 0 ; q < this.array.length ; q++ ) {
				if ( this.array[ k ].stationID == this.array[ q ].stationPID ) {
					cy = 0;
					break;
				} else {
					cy = 1;
				}
			}
			if ( cy == 1 ) {
				aee = this.array[ k ];
				break;
			} 
		}*/
		for ( var k = 0 ; k < 1000000 ; k++ ) {
			if ( this.groups[ k ] != undefined ) {
				aee = this.groups[ k ];
				break;
			}
		}
		var ccer = new Array(),
			kl = 0;
		var ccy =  this.getDom( aee , ccer , kl );
		
		return ccy;
	},
	group : function () {
		for ( var i = 0 ; i < this.array.length ; i++ ) {
			if ( this.groups[ this.array[ i ][ this.node._pid ] ] ) {
				this.groups[ this.array[ i ][ this.node._pid ] ].push( this.array[ i ] );
			} else {
				this.groups[ this.array[ i ][ this.node._pid ] ] = [];
				this.groups[ this.array[ i ][ this.node._pid ] ].push( this.array[ i ] );
			}
		}
	},
	getDom : function ( arr , ccer , kl ) {
		if ( !arr ) {
			return '';
		}
		kl = kl + 1;
		for ( var i = 0 ; i < arr.length ; i++ ) {
			var strc = '';
			if ( kl == 1 ) {
				strc = 'open';
			} else {
				strc = 'closed';
			}

			var attrs = {};

			if ( this.node._attributes != null && this.node._attributes != undefined ) {
				for ( var c = 0 ; c < this.node._attributes.length  ; c++ ) {
					attrs[ this.node._attributes[ c ] ] = arr[ i ][  this.node._attributes[ c ]  ];
				}
			}
			
			ccer[ i ] = {
				'id' : arr[ i ][ this.node._id ],
				'text' : arr[ i ][ this.node._name ],
				'state' : strc,
				'countname' : arr[ i ][ this.node._countname ],
				'attributes': attrs,
				'pid' : arr[ i ][ this.node._pid ],
				'children' : []
			};
			
			ccer[ i ].children = this.getDom( this.groups[ arr[ i ][ this.node._id ] ] , ccer[ i ].children , kl );
			if ( ccer[ i ].children == '' || ccer[ i ].children.length < 1 ) {
				delete ccer[ i ].children;
				delete ccer[ i ].state;
			}
		}
		return ccer;
	}	
}

function TreeChildsEx(showtype){
	this.showtype=showtype;
}
TreeChildsEx.prototype ={
		init:function(JsonObj,nodeField,OpenLevel){
			var nodeMap=[];
			var nodeMapIndexByPid=[];
			var rootId=0; 
			var rootList=[];
			var parentList=[];
	
			/*json转换为 以id为主键的数组*/
			$.each(JsonObj,function(){

				nodeMap[this[nodeField._id]]=this;
				if(nodeMapIndexByPid[this[nodeField._pid]]==undefined){
					nodeMapIndexByPid[this[nodeField._pid]]=[];
				}
				nodeMapIndexByPid[this[nodeField._pid]].push(this);
			});
			
			/*查找所有根节点*/
			$.each(JsonObj,function(){
				
				if(nodeMap[this[nodeField._pid]]==undefined){
					rootList.push(this[nodeField._id]);
				}
			});
			var treeNode=[]
			for(var i=0;i<rootList.length;i++){
				var nodeid=rootList[i];
				
				var nodeitem = {
						'id' : nodeMap[ nodeid ][nodeField._id  ],
						'text' : nodeMap[ nodeid ][ nodeField._name ],
						'state' : 'open',
						'countname' :0,
						'nodetype':nodeMap[ nodeid ][nodeField._nodetype ],
						'pid' : nodeMap[ nodeid ][nodeField._pid ],
						'attributes':  this.getAttrs(nodeField,nodeMap[ nodeid ]),
						'children' :''
					};
				nodeitem.countname =this.getChildrens(nodeMapIndexByPid,nodeitem,nodeField,OpenLevel,1);
				treeNode.push(nodeitem);
				
			}
			
			return treeNode;
		},
		getChildrens:function(nodemap,pnode,nodeField,OpenLevel,SelfLevel){
			var childitems=nodemap[pnode['id' ]];
			var level=SelfLevel+1;
			var state="open";
			if(level>OpenLevel){
				state="closed";
			}
			if(  childitems==undefined || childitems==null || childitems.length<=0){
				delete pnode.children;
				delete pnode.state;
				if(nodeField._countname==undefined || nodeField._countname=='' ||  pnode['countname']==undefined){
					return 0
				}else{
					return pnode['countname'];
				}
			}
			var nodelist=[];
			var count=0;
			//var showNode=[2,1995];

			
			for(var i=0;i<childitems.length;i++){
				
				var item=childitems[i];
				//if(level==2 && showNode.indexOf(item[nodeField._id ])<0){
				//	continue;
				//}
				var node = {
						'id' : item[nodeField._id ],
						'text' : item[nodeField._name ],
						'state' : state,
						'countname' :item[ nodeField._countname ],
						'pid' : item[nodeField._pid  ],
						'nodetype':item[nodeField._nodetype ],
						'attributes':  this.getAttrs(nodeField,item),
						'children' :''
					};
				count+=this.getChildrens(nodemap,node,nodeField,OpenLevel,level);
				
				nodelist.push(node);
			}
			if(pnode.nodetype>= this.showtype){
				delete pnode.children;
				delete pnode.state;
				
			}else{
				pnode.children=nodelist;
			}

			pnode.countname=count;
			return count;
		},
		getAttrs:function(nodeField,item){
			var attrs = {};

			if ( nodeField._attributes != null && nodeField._attributes != undefined ) {
				for ( var c = 0 ; c < nodeField._attributes.length  ; c++ ) {
					attrs[ nodeField._attributes[ c ] ] = item[  nodeField._attributes[ c ]  ];
				}
			}
			return attrs;
		}
}
/* tree 封装 END */













/* easyui下拉菜单 BEGIN */
$.fn[ 'ecombobox' ] = function () {
	var self = $( this ),
		settings = {
			paramname : null,
			valueField : 'id',
			textField : 'text',
			url : '',
			type : 'GET',
			width : 165,
			ajdata : null,
			cbdata : '',
			onLoadError : function () {},
			onSelect : function () {},
			onUnselect : function () {},
			firstnull : false,
			selectfirstvalue : true,
			panelHeight : 200
    	},
    	exps = {
      		init : function ( options ) {
        		settings = $.extend( settings , options );
				self.data( 'settings' , settings );
				if ( settings.url != '' ) {
			  		/* 向后台发送请求 */
			  		$( 'body' ).reajax( {
						url : settings.url,
						type : settings.type,
						data : settings.ajdata,
						fnafter : function ( data ) {
				  			var params;
				  			if(settings.paramname != null){
								params = data.content[ settings.paramname ];
				  			} else {
								params = data.content;
				  			}
							if ( settings.cbdata != '' ) {
								for ( var i = 0 ; i < params.length ; i++ ) {
									settings.cbdata.push( params[ i ] );
								}
								params = settings.cbdata;
							}
							var _aaaa = params;
							if ( settings.firstnull ) {
								_aaaa = [ {} ];
								_aaaa[ 0 ][ settings.valueField ] = '';
								_aaaa[ 0 ][ settings.textField ] = '--无--';
								for ( var i = 0 ; i < params.length ; i++ ) {
									_aaaa.push( params[ i ] );
								}
							}
				  			self.combobox({
								data : _aaaa,
								valueField : settings.valueField,
								textField : settings.textField,
								width : settings.width,
								onLoadError : settings.onLoadError,
								onSelect : function ( record ) {
									settings.onSelect( record )
								},
								onUnselect : function ( record ) {
									settings.onUnselect( record )
								},
								panelHeight : settings.panelHeight
				  			});
				  			self.combobox( 'select' , _aaaa[ 0 ][ settings.valueField ] );
						}
					} );  
				} else {
			  		/* 直接存放数据 */
					var	_bbbb = settings.cbdata;
					if ( settings.firstnull ) {
						_bbbb = [ {} ];
						_bbbb[ 0 ][ settings.valueField ] = '';
						_bbbb[ 0 ][ settings.textField ] = '--无--';
						for ( var i = 0 ; i < settings.cbdata.length ; i++ ) {
							_bbbb.push( settings.cbdata[ i ] );
						}
					}
					
			  		self.combobox({
						data : _bbbb,
						valueField : settings.valueField,
						width : settings.width,
						textField : settings.textField,
						onLoadError : settings.onLoadError,
						onSelect : function ( record ) {
							settings.onSelect( record )
						},
						onUnselect : function ( record ) {
							settings.onUnselect( record )
						},
						panelHeight : settings.panelHeight
			  		});
			  		if ( settings.selectfirstvalue ) 
			  			self.combobox( 'select' , _bbbb[ 0 ][ settings.valueField ] );
				}
		  	},
		  	options : function () {
				/* 获取初始化参数 */
				return self.combobox( 'options' );
		  	},
		  	getData : function () {
				return self.combobox( 'getData' );
		  	},
		  	loadData : function ( data ) {
				/* 加载数据 */
				self.combobox( 'loadData' , data );
		  	},
		  	reload : function () {
				/* 重新加载 */
				return self.combobox( 'reload' );
		  	},
		  	setValue : function ( datas , datasss ) {
				/* 设置选中的值 */
				var a = 0 ;
				for ( var c = 0 ; c < ( self.combobox( 'getData' ) ).length ; c++ ) {
					if ( ( self.combobox( 'getData' ) )[ c ][ self.data( 'settings' ).valueField ] == datas ) {
						a = 1;
						break;
					}
				}
				if ( a != 0 ) {
					self.combobox( 'setValue' , '--' );
					self.combobox( 'setValue' , datas );	
				} else {
					self.combobox( 'setValue' , '--' );
					self.combobox( 'setValue' , datas );
					/*self.parent().find( '.textbox-text.validatebox-text' ).attr( 'value' , datasss );*/
				}
		  	},
			getValue : function () {
				/* 获取被选中的值 */
				return self.combobox( 'getValue' );
			},
			clear : function () {
				/* 清空选中的内容 */
				return self.combobox( 'clear' );
			},
			select : function ( value ) {
				/* 选中某一行 */
				return self.combobox( 'select' , value );
			},
			unselect : function () {
				/* 取消所有选中 */
				return self.combobox( 'unselect' );
			},
			object : function () {
				/* 返回调用对象 */
				return self;
			},
			editable : function ( str ) {
				/* 是否可编辑 */
				self.combobox( { editable : str } ); 
			}
		}
  return exps;
}
/* easyui下拉菜单 END */











/* 顶部导航加载 BEGIN */
$.fn[ 'iHeader' ] = function ( options ) {
	if ( !$( this ).attr( 'class' ) && !$( this ).attr( 'id' ))
		return 0;
	var self = $( this ),
		array;
	$( 'body' ).reajax({
		url : 'menu/belongToken/batch/read.do',
		fnafter : function ( data ) {
			array = data.content.menus;
			var _html = new Childs( array ).init( 0 );
			self.append( _html );
			
			$( '.body-tabwrap' ).bodycontent().init();
			
			$( '.brand' ).click( function () {
				var _self = $( this );
				$( '.header-main-nav>ul>li' ).removeClass( 'nacactive' );
				_self.addClass( 'nacactive' );
				var _xwidth = $( window ).height() - $( '.header' ).height() - 28;
				$( '.body-tabwrap' ).bodycontent().add( {
					title : _self.find( 'h4' ).text(),
					name : 'home',
					content : '<iframe id="home" scrolling="auto" frameborder="0"  src="nav-child-page/home2.html" style="width:100%;height:' + _xwidth + 'px;"></iframe>'
				} );
				$( window ).resize( function () {
					$( '#home' ).css( {
						'width' : $( window ).width() + 'px',
						'height' : $( window ).height() - $( '.header' ).height() - 28 + 'px'
					} );
				} );
			} );
			
			$( '.header-main-nav a' ).click( function() {
				var _self = $( this );
				
				$( '.brand' ).removeClass( 'nacactive' );
				_self.parent().parent().parent().parent().find( '>li' ).removeClass( 'nacactive' );
				_self.parent().parent().parent().addClass( 'nacactive' );
				
				if ( _self.parent().find( '.dropdown-menu' ).length < 1 ) {
					var _id = _self.find( '.nav-id' ).text(),
						text = _self.find( '.nav-title' ).text();
					var _xwidth = $( window ).height() - $( '.header' ).height() - 28;
					$( '.body-tabwrap' ).bodycontent().add( {
						title : text,
						name : self.find( '.nav-id' ).text(),
						content : '<iframe id="' + _self.find( '.nav-id' ).text() + '" scrolling="auto" frameborder="0"  src="nav-child-page/' + _self.find( '.nav-id' ).text() + '.html" style="width:100%;height:' + _xwidth + 'px;"></iframe>'
					} );
					$( window ).resize( function () {
						$( '#' + _self.find( '.nav-id' ).text() ).css( {
							'width' : $( window ).width() + 'px',
							'height' : $( window ).height() - $( '.header' ).height() - 28 + 'px'
						} );
					} );
				}
				$( '.tabs-inner' ).off( 'click' );
				$( '.tabs-inner' ).on( 'click' , function () {
					var _self = $( this ),
						_name = _self.find( '.tabs-title' ).text();
					if ( _name == '首页' ) {
						$( '.brand' ).addClass( 'nacactive' );
						$( '.navbar.hor-menu li' ).removeClass( 'nacactive' );
					} else if ( _name == '欢迎' ) {
						$( '.wellcomenav' ).addClass( 'nacactive' );
						$( '.navbar.hor-menu li' ).removeClass( 'nacactive' );
					} else {
						for ( var c = 0 ; c < $( '.navbar.hor-menu li' ).length ; c++ ) {
							if ( _name == $( '.navbar.hor-menu li' ).eq( c ).find( '>a .nav-title' ).text() ) {
								if ( $( '.navbar.hor-menu li' ).eq( c ).parent().parent().find( '>a' ).length > 0 ) {
									$( '.navbar.hor-menu li' ).removeClass( 'nacactive' );
									$( '.brand' ).removeClass( 'nacactive' );
									$( '.navbar.hor-menu li' ).eq( c ).parent().parent().addClass( 'nacactive' );
								} else {
									$( '.navbar.hor-menu li' ).removeClass( 'nacactive' );
									$( '.brand' ).removeClass( 'nacactive' );
									$( '.navbar.hor-menu li' ).eq( c ).addClass( 'nacactive' );
								}
							}
						}
					}
				} );
			} );
		}
	});
}
function Childs( arr ) {
	this.array = arr || [];
	this.groups = {};
}
Childs.prototype = {
	init : function ( allparent ) {
		this.group();
		return this.getDom( this.groups[ allparent ] );
	},
	group : function () {
		for ( var i = 0 ; i < this.array.length ; i++ ) {
			if ( this.groups[ this.array[ i ].menuPid ] ) {
				this.groups[ this.array[ i ].menuPid ].push( this.array[ i ] );
			} else {
				this.groups[ this.array[ i ].menuPid ] = [];
				this.groups[ this.array[ i ].menuPid ].push( this.array[ i ] );
			}
		}
	},
	getDom : function ( arr ) {
		if ( !arr ) {
			return '';
		}
		var html = '<ul class="dropdown-menu">';
		for ( var i = 0 ; i < arr.length ; i++ ) {
			html += '<li class="">' +
						'<a class="dropdown-toggle" data-toggle="dropdown">' +
							'<span class="nav-icon">' + arr[ i ].menuIcon + '</span>' +
							'<span class="nav-title">' + arr[ i ].menuName + '</span>' +
							'<span class="nav-id nav-id-' + arr[ i ].menuId + '">' + arr[ i ].menuUrl + '</span>' +
							'<span class="arrow"></span>' +
						'</a>';
			html += this.getDom( this.groups[ arr[ i ].menuId ] );
			html += '</li>';
		}
		html += '</ul>';
		return html;
	}	
}
/* 顶部导航加载 END */








/* 判断浏览器版本 BEGIN */
/*$.fn[ 'BrowserVersion' ] = function () {
	if ( !$( this ).attr( 'class' ) && !$( this ).attr( 'id' ))
		return 0;
	var isIE = !!window.ActiveXObject;
	var isIE6 = isIE&&!window.XMLHttpRequest;
	var isIE8 = isIE&&!!document.documentMode;
	var isIE7 = isIE&&!isIE6&&!isIE8;
	if ( isIE ) {
		if ( isIE6 ) {
		
		} else if ( isIE8 ) {
			
		} else if ( isIE7 ){
			
		}
	}
}*/
/* 判断浏览器版本 END */





/* 导出 BRGIN */
$.fn[ 'dataOutPut' ] = function ( dataurl , datas ,downloadSuccess ) {
	if ( datas != '' ) {
		$( 'body' ).reajax( {
			url : dataurl,
			data : datas,
			fnafter : function ( data ) {
				console.log(data)
				location.href = _url + 'file/download.do?token=' + store.get( 'token' ) + '&fileName=' + data.content.uri ;
				if(typeof downloadSuccess == 'function'){
			    	downloadSuccess();
			  	}
			},
			fnsource : function ( data ) {
				$( 'body' ).RemindWoken( '无数据' );
				if(typeof downloadSuccess == 'function'){
          			downloadSuccess();
        		}
			}
		} );
	} else {
		$( 'body' ).reajax( {
			url : dataurl,
			fnafter : function ( data ) {
			  location.href = _url + 'file/download.do?token=' + store.get( 'token' ) + '&fileName=' + data.content.uri ;
        if(typeof downloadSuccess == 'function'){
            downloadSuccess();
          }
			},
			fnsource : function ( data ) {
				$( 'body' ).RemindWoken( '无数据' );
				if(typeof downloadSuccess == 'function'){
          			downloadSuccess();
        		}
			}
		} );
	}
}
/* 导出 END */







/* 进度条 BEGIN */
$.fn[ 'eprogressbar' ] = function () { 
	var self = $( this ),
    	settings = {
			pwidth : 255,
			status : true,
			width : 185,
			height : 25,
			value : 0,
			text : '',
			onchange : function () {
			},
			loading : false
    	},
    	exps = {
      		init : function ( options ) {
        		if ( !self.attr( 'class' ) && !self.attr( 'id' ))
          			return 0;
        		settings = $.extend( settings , options );
				var status = ( settings.status ? '<h4 class="progress-status" style="float:left;font-size:25px;width:25px;height:25px;margin:0 10px 0 0;"><i class="icon-spinner icon-spin"></i></h4>' : '' ),
					progressc = '';
				if ( settings.loading ) {
					progressc = '<div class="progress-container" style="position:absolute;width:100%;height:100%;background:rgba( 0,0,0,0.4 );z-index:1000000">' +
									'<div class="progress-content" style="position:absolute;width:175px;background:rgba(255,255,255,0.85);padding:15px;border-radius:4px !important;">' +
										status + 
										'<div class="progress-content-title" style="width:100%;height:30px;line-height:30px;text-align:center;letter-spacing:1px;font-size:17px;clear:both;">需要一些时间..</div>' +
									'</div>' +
								'</div>';
					self.append( progressc );
					
				} else {
					progressc = '<div class="progress-container" style="position:absolute;width:100%;height:100%;background:rgba( 0,0,0,0.4 );z-index:1000000">' +
									'<div class="progress-content" style="position:absolute;width:' + settings.pwidth + 'px;background:rgba(255,255,255,0.85);padding:15px;border-radius:4px !important;">' +
										status + 
										'<div class="progress-bar" style="width:' + settings.width + 'px;float:left;margin:3px 0;"></div>' + 
										'<div class="progress-content-title" style="width:100%;height:30px;line-height:30px;text-align:center;letter-spacing:1px;font-size:17px;clear:both;">需要一些时间..</div>' +
									'</div>' +
								'</div>';	
					self.append( progressc );
					self.find( '.progress-bar' ).progressbar({
						width : settings.width,
						height : settings.height,
						value : settings.value,
						text : settings.text,
						onChange : function ( value ) {
							settings.onchange( value );
						}
					});
				}
				self.find( '.progress-content' ).css({
					'top' : self.height()/2 - settings.height/2 - 15 + 'px',
					'left' : self.width()/2 - self.find( '.progress-content' ).width()/2 + 'px'
				});
	  		},
			loadingsuccess : function () {
				
			},
			loadingerror : function () {
			
			},
			interval : function ( itime ) {
				/* 按照自定义事件读取进度条 */
				var i = 0 ;
				setInterval( function () {
					exps.setvalue( i++ );
					if ( i == 100 ) {
						exps.distory();
					}
				} , ( itime * 1000 )/100 );
			},
			distory : function () {
				/* 销毁进度条 */
				self.find( '.progress-container' ).remove();
			},
			setvalue : function ( data ) {
				/* 设置进度值 */
				self.find( '.progress-bar' ).progressbar( 'setValue' , data );
			},
			getvalue : function () {
				/* 获取进度值 */
				self.find( '.progress-bar' ).progressbar( 'getValue' );
			}
    	}
  	return exps;
}
/* 进度条 END */





/* 按钮点击后等待事件 BEGIN */
$.fn[ 'buttonstatus' ] = function () { 
	var self = $( this ),
    	settings = {
			title : '执行中..'
    	},
    	exps = {
      		init : function ( options ) {
        		if ( !self.attr( 'class' ) && !self.attr( 'id' ))
          			return 0;
        		settings = $.extend( settings , options );
        		var style = self.attr('style');
        		self.hide();
        		self.before(
        		        '<div class="button-status-content ' + self.attr( 'class' ) + '" style="' + style + '" >' +
        		        '<h6 class="button-status" style="float:left;font-size:15px;width:15px;height:15px;margin:2px 5px 0 0;line-height:15px;"><i class="icon-spinner icon-spin"></i></h6>' +
        		        '<div class="button-status-title" style="float:left;line-height:25px;letter-spacing:1px;">' + settings.title + '</div>' +
        		        '</div>'
        		);
	  		},
			loadingsuccess : function () {
				/* 执行成功后 */
				self.parent().find( '.button-status' ).html( '<i class="icon-ok"></i>' );
				self.parent().find( '.button-status-title' ).html( '执行成功.' );
			},
			loadingerror : function () {
				/* 执行失败后 */
				self.parent().find( '.button-status' ).html( '<i class="icon-remove"></i>' );
				self.parent().find( '.button-status-title' ).html( '执行失败.' );
			},
			interval : function ( itime ) {
				/* 设定按钮的执行时间 */
				var i = 0 ;
				setInterval( function () {
					i++;
					if ( i == 100 ) {
						exps.distory();
					}
				} , ( itime * 1000 )/100 );
			},
			distory : function () {
				/* 销毁 */
				self.parent().find( '.button-status-content' ).remove();
				self.show();
			},
			setvalue : function ( str ) {
				/* 设置按钮的显示值 */
				self.parent().find( '.button-status-title' ).progressbar( 'setValue' , str );
			}
    	}
  	return exps;
}
/* 按钮点击后等待事件 END */







/* 上传excel BEGIN */
$.fn[ 'FileuploadFN' ] = function ( options ) {
	if ( !$( this ).attr( 'class' ) && !$( this ).attr( 'id' ))
		return 0;
	var self = $( this ),
		settings = $.extend({
				isclasstitle : '',		/* 导入框标题 */
				ajaxFileUploadurl : '',	/* 上传方法 */
				FileUploadID : '',		/* 上传文本框id */
				FileUploadpresent : '',	/* 导入进度 */
				freefn : '',			/* 指定导入完成后执行的方法名 */
				freefnobj : {},			/* 导入完成后执行的方法的传入参数 */
				title : '导入信息'
		} , options ),
		_isclasstitle = settings.isclasstitle,
		_ajaxFileUploadurl = settings.ajaxFileUploadurl,
		_FileUploadID = settings.FileUploadID,
		_FileUploadpresent = settings.FileUploadpresent,
		_freefn = settings.freefn,
		_freefnobj = settings.freefnobj;
	var	timeinter = clearInterval( timeinter ),
		_sel = $( this );
	self.epanelhistory().init({
		data : '<div class="organization-body">' +
					'<div class="organization-detail">' +
						'<ul></ul>' +
					'</div>' +
					'<div class="organization-tool-wrap">' +
						'<div class="organization-tool">' +
							'<span class="organization-tool-input-button uploadbtn">开始导入</span>' +
						'</div>' +
						'<div class="organization-tool">' +
							'<span class="organization-tool-scan-button">浏览文件</span>' +
						'</div>' +
					'</div>' +
					'<input type="file" id="uploadexcel" name="file" class="upload_file" />' +
				'</div>',
		title : settings.title,
		_width : 500,
		_height : 265,
		_id : '',
		_class : '',
		fnafter : function ( a ) {
			
		}
	});
	
	$( '.file-wrap' ).append( 
		'<div class="organization-input">' +
			'<div class="organization-header">' +
    			settings.title +
        		'<span class="organization-remove">关闭</span>' +
    		'</div>' +
    		'<div class="organization-body">' +
        		'<div class="organization-detail">' +
        			'<ul></ul>' +
        		'</div>' +
        		'<div class="organization-tool-wrap">' +
        			'<div class="organization-tool">' +
                		'<span class="organization-tool-input-button uploadbtn">开始导入</span>' +
            		'</div>' +
        			'<div class="organization-tool">' +
                		'<span class="organization-tool-scan-button">浏览文件</span>' +
            		'</div>' +
        		'</div>' +
        		'<input type="file" id="uploadexcel" name="file" class="upload_file" />' +
    		'</div>' +
		'</div>'
	);
	$( '.organization-remove' ).on( 'click' , function () {
		$( this ).parent().parent().parent().remove();
	} );
	$( '#uploadexcel' ).on( 'mouseover' , function () {
		$( '.organization-tool-scan-button' ).addClass( 'active' );
		$( this ).on( 'mouseout' , function () {
			$( '.organization-tool-scan-button' ).removeClass( 'active' );
		} );
	} );
	var uploadfile = '' , filesizeable , isclick = 0;
	$( '#uploadexcel' ).on( 'change' , function () {
		var _self = $( this ),
			filesize = _self.get( 0 ).files[ 0 ];
			uploadfile = _self.val();
		if ( filesize.size > 1024 * 1024 ) {
			filesizeable = ( Math.round( filesize.size * 100 / (1024 * 1024)) / 100 ).toString() + 'MB';
		} else {
			filesizeable = ( Math.round( filesize.size * 100 / 1024) / 100 ).toString() + 'KB'
		}
		$( '.organization-detail > ul' ).html( '<li>' +
													'<span class="input-string">' +
														'文件名: ' + uploadfile + 
													'</span>' +
													'<span class="filesize">' +
														'文件大小: ' + filesizeable + 
													'</span>' +
													'<span class="upload-timebar"></span>' +
													'<span class="upload-remove">移除</span>' +
											   '</li>' );
		$( '.upload-remove' ).on( 'click' , function () {
			$( this ).parent().remove();
		} );
	} );
	$( '.uploadbtn' ).on( 'click' , function(){
		var lcs = Ladda.create(this);
		lcs.start();
		if ( isclick == 0 ) {
			var sel = $( this );
			var OP = uploadfile.lastIndexOf( '.' ),
				SU = uploadfile.substring( OP + 1 );
			if ( uploadfile == '' ) {
				lcs.stop();
				$( 'body' ).RemindWoken( '请选择要导入的文件' );
				return;
			}
			if ( !(uploadfile != '' && ( SU == 'xlsx' || SU == 'xls' ) && filesizeable.replace( 'KB' , '' ) < 100000000 && filesizeable.replace( 'KB' , '' ) > 0) ) {
				lcs.stop();
				$( 'body' ).RemindWoken( '上传文件不符合' );
				return;
			}
			isclick = 1;
			$( '.organization-tool span' ).addClass( 'unused' );
			$( '.upload_file' ).css( 'display' , 'none' );
			$.ajaxFileUpload({
				url : _url + _ajaxFileUploadurl,
				dataType : 'json',
				secureuri : false,
				fileElementId : _FileUploadID,
				success : function ( data , status ) {
					lcs.stop();
					if ( jQuery.parseJSON( jQuery( data ).text() ).state == 'SUCCESS' ) {
						$( '.upload-timebar' ).html( '导入进度: ' + data.content );
						$( 'body' ).RemindWokenSuccess( '导入完成' );
						$.ajax({
							type : 'GET',
							url : _url + _FileUploadpresent ,
							success : function ( data ) {
								if ( data.state == 'SUCCESS' ) {
									$( '.upload-timebar' ).html( '导入进度: ' + data.content );
									isclick = 0;
									window.location.replace( _url + folder_a + folder_b + folder_c + folder_p + folder_e );
								} else if ( data.state == 'INVALID' ) {
									isclick = 0;
									$( 'body' ).RemindWoken( word_c );
								} else {
									isclick = 0;
									$( 'body' ).RemindWokenError( '导入失败' );
								}
							},
							error : function ( a , b , c ) {
								if ( a.status == 0 ) {
									isclick = 0;
									$( 'body' ).RemindWokenError( word_d );
								} else {
									isclick = 0;
									$( 'body' ).RemindWokenError( '导入失败' );
								}
							}
						});
						setTimeout( function () {
							sel.parent().parent().parent().parent().parent().remove();
							isclick = 0;
							$( '.organization-tool span' ).removeClass( 'unused' );
							$( '.upload_file' ).css( 'display' , 'block' );
							eval( _freefn )( _freefnobj );
						} , 3500 );
						clearInterval( timeinter );		
					} else if ( data.state == 'INVALID' ) {
						isclick = 0;
						$( 'body' ).RemindWoken( word_c );
					} else {
						$( 'body' ).RemindWokenError( '导入出错' );
						isclick = 0;
						clearInterval( timeinter );
					}
				},
				error : function ( data , status , e ) {
					lcs.stop();
					isclick = 0;
					$( 'body' ).RemindWokenError( '导入失败' );
					clearInterval( timeinter );
				}
			});
			timeinter = setInterval( function () {
				$.ajax({
					type : 'GET',
					url : _url + _FileUploadpresent ,
					success : function ( data ) {
						if ( data.state == 'SUCCESS' ) {
							$( '.upload-timebar' ).html( '导入进度: ' + data.content );
						} else if ( data.state == 'INVALID' ) {
							isclick = 0;
							$( 'body' ).RemindWoken( word_c );
						} else {
							isclick = 0;
							$( 'body' ).RemindWokenError( '导入失败' );
						}
					},
					error : function ( a , b , c ) {
						if ( a.status == 0 ) {
							isclick = 0;
							$( 'body' ).RemindWokenError( word_d );
						} else {
							isclick = 0;
							$( 'body' ).RemindWokenError( '导入失败' );
						}
					}
				});
			} , 500 );
		}
		preventdefault;
	});
}
/* 上传excel END */







/* 弹出层 BEGIN */
$.fn[ 'epanel' ] = function () {
	var self = $( this ),
		settings = {
			data : '',
			title : '',
			_width : 565,
			_height : 445,
			_id : '',
			_class : '',
			fnafter : function ( a ) {
				
			},
			fnhidden: function(){
			
			},
			isfloor : true,
			z_index : '11000'
		},
		
		exps = {
			init : function ( options ) {
				if ( !self.attr( 'class' ) && !self.attr( 'id' ))
					return 0;
				self.css( 'display' , 'block' );
				settings = $.extend( settings , options );
				self.data("settings",settings);
				self.css( 'position' , 'relative' );
				self.wrap( '<div class="table-edit-wrap-container"></div>' );
				self.parent().wrap( '<div class="table-edit-wrap container-epanel"></div>' );
				if ( settings.isfloor ) {
					self.parent().parent().append(
						'<div class="table-edit-wrap-content-title">' +
							( self.attr( 'title' ) == undefined ? '' : self.attr( 'title' ) ) +
							'<span class="excute-button">关闭</span>' +
						'</div>'
					);
				} else {
					self.parent().parent().append(
						'<div class="table-edit-wrap-content-title">' +
							( self.attr( 'title' ) == undefined ? '' : self.attr( 'title' ) ) +
						'</div>'
					);
				}
				
				var _xxxxx = self.attr( 'class' ) == undefined ? '' : self.attr( 'class' ),
					_ddddd = self.attr( 'id' ) == undefined ? '' : self.attr( 'id' );
				self.parent().parent().wrap( '<div class="table-edit-wrap " id=""></div>' );
				self.parent().parent().find( '.excute-button' ).click( function () {
					exps.hidepanel();
				} );
				
				self.parent().parent().parent().find( '.container-epanel' ).css( {
					height : settings._height,
					width : settings._width,
					top : self.parent().parent().parent().parent().height()/2 - settings._height/2 + 'px',
					left : self.parent().parent().parent().parent().width()/2 - settings._width/2 + 'px'
				} );
				$( window ).resize( function () {
					self.parent().parent().parent().find( '.container-epanel' ).css( {
						height : settings._height,
						width : settings._width,
						top : self.parent().parent().parent().parent().height()/2 - settings._height/2 + 'px',
						left : self.parent().parent().parent().parent().width()/2 - settings._width/2 + 'px'
					} );
				} );
				self.attr( 'title' , '' );
				self.parent().parent().parent().css( 'visibility' , 'hidden' );
				exps.dragpanel();
				settings.fnafter( self.parent().parent().parent() );
				
			},
			remove : function () {
				/* 移除弹出层 */
				self.parent().parent().parent().remove();
			},
			hidepanel : function () {
			  settings.fnhidden();
				/* 隐藏弹出层 */
				self.parent().parent().parent().css( 'visibility' , 'hidden' );
			},
			showpanel : function () {
				/* 显示弹出层 */
				self.parent().parent().parent().css( 'visibility' , 'visible' );
			},
			dragpanel : function () {
				/* 拖动弹出层 */
				self.parent().parent().parent().find( '.table-edit-wrap .table-edit-wrap-content-title' ).on( 'mousedown' , function ( e ) {
					e = window.event || e;
					var _self = $( this ),
						disX = e.clientX - _self.parent().offset().left,
						disY = e.clientY - _self.parent().offset().top,
						disXX = _self.parent().parent().parent().offset().left,
						disYY = _self.parent().parent().parent().offset().top,
						flag = true;
					_self.css( 'cursor' , 'move' );
					doc.on( 'mousemove' , function( ev ){
						if ( flag ) {
							ev = ev || _window.event;
							var maxL = _self.parent().parent().parent().width() - _self.parent().width(),
								maxT = _self.parent().parent().parent().height() - _self.parent().height(),
								iL = ev.clientX - disXX - disX,
								iT = ev.clientY - disYY - disY;
								iL <= 0 && ( iL = 0 );
								iL >= maxL && ( iL = maxL );
								iT <= 0 && ( iT = 0 );
								iT >= maxT && ( iT = maxT );
							_self.parent().css({ 
								'top' : iT + 'px' ,
								'left' : iL + 'px'
							});
						}
					});
					doc.on( 'mouseup' , function(){
						flag = false;
						_self.css( 'cursor' , 'default' );
					})
				} );
			},
			object : function () {
				/* 弹出层对象 */
				return self.parent().parent();
			}
		}
	return exps;
}

/* epanelhistory仅被table的隐藏显示列调用 */
$.fn[ 'epanelhistory' ] = function () {
	var self = $( this ),
		settings = {
			data : '',
			title : '',
			_width : 750,
			_height : 500,
			_id : '',
			_class : '',
			fnafter : function ( a ) {
				
			}
		},
		
		exps = {
			init : function ( options ) {
				if ( !self.attr( 'class' ) && !self.attr( 'id' ))
					return 0;
				settings = $.extend( settings , options );
				self.css( 'position' , 'relative' );
				self.append(
					'<div id="' + settings._id + '" class="table-edit-wrap ' + settings._class + '"  >' +
						'<div class="table-edit-wrap container-epanel">' +
							'<div class="table-edit-wrap-content-title">' +
								settings.title +
								'<span class="excute-button">关闭</span>' +
							'</div>' +
							'<div class="table-edit-wrap-container">' +
								settings.data +
							'</div>' +
						'</div>' +
					'</div>'
				);
				self.find( '.excute-button' ).click( function () {
					exps.removepanel();
				} );
				self.find( '.container-epanel' ).css( {
					height : settings._height,
					width : settings._width,
					top : self.height()/2 - settings._height/2 + 'px',
					left : self.width()/2 - settings._width/2 + 'px'
				} );
				$( window ).resize( function () {
					self.find( '.container-epanel' ).css( {
						height : settings._height,
						width : settings._width,
						top : self.height()/2 - settings._height/2 + 'px',
						left : self.width()/2 - settings._width/2 + 'px'
					} );
				} );
				exps.dragpanel();
				settings.fnafter( self );
			},
			removepanel : function () {
				self.find( '.table-edit-wrap' ).remove();
			},
			dragpanel : function () {
				self.find( '.table-edit-wrap .table-edit-wrap-content-title' ).on( 'mousedown' , function ( e ) {
					e = window.event || e;
					var _self = $( this ),
						disX = e.clientX - _self.parent().offset().left,
						disY = e.clientY - _self.parent().offset().top,
						disXX = _self.parent().parent().parent().offset().left,
						disYY = _self.parent().parent().parent().offset().top,
						flag = true;
					_self.css( 'cursor' , 'move' );
					doc.on( 'mousemove' , function( ev ){
						if ( flag ) {
							ev = ev || _window.event;
							var maxL = _self.parent().parent().parent().width() - _self.parent().width(),
								maxT = _self.parent().parent().parent().height() - _self.parent().height(),
								iL = ev.clientX - disXX - disX,
								iT = ev.clientY - disYY - disY;
								iL <= 0 && ( iL = 0 );
								iL >= maxL && ( iL = maxL );
								iT <= 0 && ( iT = 0 );
								iT >= maxT && ( iT = maxT );
							_self.parent().css({ 
								'top' : iT + 'px' ,
								'left' : iL + 'px'
							});
						}
					});
					doc.on( 'mouseup' , function(){
						flag = false;
						_self.css( 'cursor' , 'default' );
					})
				} );
			}
		}
	return exps;
}
/* 弹出层 END */








/* 重新封装jqueryajax BEGIN */
$.fn[ 'reajax' ] = function ( options ) {
	var self = $( this ),
		settings = {
			url : '',
			type : 'GET',
			data : '',
			async : true,
			fnafter : function () {
			
			},
			insuccess : function () {
			
			},
			invaliderror : function () {
			
			},
			aftersuccess : function () {
			
			},
			inerror : function () {
				
			},
			header : '',
			contentType : 'application/x-www-form-urlencoded',
			fnerror : function () {
			
			},
			fnsource : function () {
			
			},
			fntarget : function () {
				window.parent.document.location.replace( _url );
			},
			fnerrormessage : function(data){
				if ( data.message != undefined ) {
					if ( data.message.notice != undefined ) {
						$( 'body' ).RemindWokenError( data.message.notice );
					} else {
						$( 'body' ).RemindWokenError( '操作失败，请联系管理员' );
					}
				}
			},
			targethtmlclass : '.ar_cars-LOGIN'
		},
	settings = $.extend( settings , options );
	var token = ( store.get( 'token' ) == undefined ? '' : store.get( 'token' ) );
	/*console.log(token);*/

	if ( token == '' && $( settings.targethtmlclass ).length < 1 ) {
		settings.fntarget();
	} else {
		$.ajax({
			type : settings.type,
			contentType : settings.contentType,
			url : _url + settings.url ,
			data : settings.data,
			async : settings.async,
			headers : { 'Access-Token' : token },
			success : function ( data ) {
				settings.insuccess( data );
				if ( data.status == 'SUCCESS' ) {
					settings.fnafter( data );
				} else if ( data.status == 'INVALID' ) {
					$( 'body' ).RemindWoken( word_c );
					settings.invaliderror( data );
				} else if ( data.status == 'NOTACCESSIBLE' ) {
					settings.fntarget();
				} else if ( data.status == 'EXCEPTION' ) {
					settings.invaliderror( data );
				} else if ( data.status == 'ERROR' ) {
					settings.fnerrormessage(data);
					settings.inerror( data );
				} else if ( data.status == 'NORESOURCES' ) {
					settings.fnsource( data );
				} else {
					if ( settings.fnerror == '' ) {
						if ( data.message != undefined ) {
							if (  data.message.notice != undefined ) {
								$( 'body' ).RemindWokenError( data.message.notice );	
							} else if ( data.message.notice != undefined ) {
								$( 'body' ).RemindWokenError( data.message.notice );
							} else if ( data.message.cause != undefined ) {
								$( 'body' ).RemindWokenError( data.message.notice );
							} else {
								$( 'body' ).RemindWokenError( '操作失败' );
							}
						} else {
							$( 'body' ).RemindWokenError( '操作失败' );
						}
					} else {
						settings.fnerror( data );
					}
					settings.aftersuccess( data );
				}
			},
			error : function ( a , b , c ) {
				settings.inerror();
				if ( a.status == 0 ) {
					$( 'body' ).RemindWokenError( word_d );
				} else {
					$( 'body' ).ErrorTarget( a.status );
				}
			}
		});
	}
}
/* 重新封装jqueryajax END */





/* easyui tab 封装 BEGIN */
$.fn[ 'etabs' ] = function () {
	var self = $( this ),
		selfp = $( this ).parent(),
		settings = {
			width : 300,
			height : 300,
			fit : true,
			tools : '',
			toolPosition : 'right',
			tabPosition : 'left',
			headerWidth : 200,
			tabHeight : 35,
			selected : 0,
			showHeader : true,
			onSelect : function () {
				
			},
			onLoad : function () {
			
			},
			onBeforeClose : function () {
			
			},
			onClose : function () {
			
			},
			onAdd : function () {
			
			},
			onUpdate : function () {
				
			}
		},
		exps = {
			init : function ( options ) {
				if ( !self.attr( 'class' ) && !self.attr( 'id' ))
					return 0;
				settings = $.extend( settings , options );
				self.tabs( {
					width : settings.width,
					height : settings.height,
					fit : settings.fit,
					tools : settings.tools,
					/*toolPosition : settings.toolPosition,
					tabPosition : settings.tabPosition,*/
					headerWidth : settings.headerWidth,
					tabHeight : settings.tabHeight,
					selected : settings.selected,
					showHeader : settings.showHeader,
					onSelect : function ( title , index ) {
						settings.onSelect( title , index );
					},
					onLoad : function ( title , index ) {
						settings.onLoad( title , index );
					},
					onBeforeClose : function ( title , index ) {
						settings.onBeforeClose( title , index );
					},
					onClose : function ( title , index ) {
						settings.onClose( title , index );
					},
					onAdd : function ( title , index ) {
						settings.onAdd( title , index );
					},
					onUpdate : function ( title , index ) {
						settings.onUpdate( title , index );
					}
				} );
				if ( settings.fit ) {
					$( window ).resize( function () {
						var _width = selfp.width() + 'px',
							_height = selfp.height() + 'px';
						self.tabs( {
							width : _width,
							height : _height,
						} );
					} );
				}
				exps.removebutton();
			},
			taboptions : function () {
				return self.tabs( 'options' );
			},
			tabs : function () {
				return self.tabs( 'tabs' );
			},
			resize : function () {
				self.tabs( 'resize' );
			},
			add : function ( options ) {
				self.tabs( 'add' , options );
			},
			close : function ( panel ) {
				self.tabs( 'close' , panel );
			},
			getTab : function ( panel ) {
				return self.tabs( 'getTab' , panel );
			},
			getTabIndex : function ( tab ) {
				return self.tabs( 'getTabIndex' , tab );
			},
			getSelected : function () {
				return self.tabs( 'getSelected' );
			},
			select : function ( panel ) {
				self.tabs( 'select' , panel );
			},
			unselect : function ( panel ) {
				self.tabs( 'unselect' , panel );
			},
			showHeader : function () {
				self.tabs( 'showHeader' );
			},
			hideHeader : function () {
				self.tabs( 'hideHeader' );
			},
			exists : function ( panel ) {
				return self.tabs( 'exists' , panel );
			},
			update : function ( tabs , eoptions ) {
				self.tabs( {
					tab : tabs,
					options : eoptions
				} );
			},
			enableTab : function ( panel ) {
				self.tabs( 'enableTab' , panel );
			},
			disableTab : function ( panel ) {
				self.tabs( 'disableTab' , panel );
			},
			scrollBy : function ( deltaX ) {
				self.tabs( 'scrollBy' , deltaX );
			},
			object : function () {
				return self;
			},
			removebutton : function () {
				$( '.body-tabwrap>.tabs-header>.tabs-wrap>ul' ).find( '.removetab' ).remove();
				$( '.body-tabwrap>.tabs-header>.tabs-wrap>ul>li>a' ).append( '<span class="removetab"><i class="icon-remove"></i></span>' );
				$( '.removetab' ).click( function () {
					var _self = $( this );
					exps.removes( _self );
				} );
			},
			removes : function ( obj ) {
				self.tabs( 'close' , obj.parent().find( '.tabs-title' ).text() );
			}
		}
	return exps;
}
/* easyui 封装 end */






/* 全局tab框架 此方法仅被导航和主框架使用 BEGIN */
var ktimers;
$.fn[ 'bodycontent' ] = function () {
	var self = $( this ),
		settings = {
			height : '',
			tabindex : ''
		},
		exps = {
			init : function ( options ) {
				if ( !self.attr( 'class' ) && !self.attr( 'id' ))
					return 0;
				settings = $.extend( settings , options );
				var lastTabs = new Array();
				self.tabs( {
					width : $( window ).width() + 'px',
					height : $( window ).height() - 70 + 'px'
				} );
				$( window ).resize( function () {
					var _width = $( window ).width() + 'px',
						_height = $( window ).height() - 70 + 'px',
						 axx = self.find( '> .tabs-header .tabs-selected .tabs-title' ).text();
					self.tabs( {
						width : _width,
						height : _height
					} );
					self.tabs( 'select' , axx );
					self.find( '.panel' ).css({
						width : _width,
						height : _height
					});
					self.find( '.panel-body' ).css({
						width : _width,
						height : _height
					});
				} );
				exps.refreshbutton();
			},
			add : function ( data ) {
				var addsettings = {
					title : '',
					name : '',
					pagepath : ''
				};
				addsettings = $.extend( addsettings , data );
				var tabsin = self.find( '.tabs-title' ),
					tabsflag = 0;
				for ( var i = 0 ; i < tabsin.length ; i++ ) {
					if ( tabsin.eq( i ).text() == addsettings.title ) {
						/* 存在则选中 */
						tabsflag = 1;
						settings.tabindex = addsettings.title; 
						
						self.tabs( 'select' , addsettings.title );
						break;
					}
				}
				if ( tabsflag != 1 ) {
	 				/* 不存在则创建 */
					self.tabs( 'add' , {
						title : addsettings.title,
						content : addsettings.content, 
						closable : true
					} );
					settings.tabindex = addsettings.title;
					exps.refreshbutton();
				}
			},
			refreshbutton : function () {
				$( '.body-tabwrap>.tabs-header>.tabs-wrap>ul' ).find( '.el-btn' ).remove();
				$( '.body-tabwrap>.tabs-header>.tabs-wrap>ul>li>a.tabs-inner' ).append( 
					'<a class="el-btn refreshbutton"><i class="icon-refresh"></i></a>'
				);
				$( '.refreshbutton' ).click( function () {
					var _self = $( this ),
						names = _self.parent().parent().find( '.tabs-title' ).text(),
						navs = $( '.header .header-main-nav li' ),
						newmin = '';
					for ( var c = 0 ; c < navs.length ; c++ ) {
						if ( names == '首页' ) {
							newmin = 'home';
							break;
						}
						if ( names == '主界面' ) {
							newmin = 'wellcome';
							break;
						}
						if ( names == navs.eq( c ).find( '>a .nav-title' ).text() && navs.eq( c ).find( '>a .nav-id' ).length > 0 ) {
							newmin = navs.eq( c ).find( '>a .nav-id' ).text();
							break;
						}
					}
					if ( newmin == 'home' ) {
						$( '#' + newmin ).attr( 'src' , 'nav-child-page/' + newmin + '2.html' );
					} else if ( newmin == 'wellcome' ) {
						$( '#' + newmin ).attr( 'src' , 'nav-child-page/page-wellcome/wellcome.html' );
					} else {
						$( '#' + newmin ).attr( 'src' , 'nav-child-page/' + newmin + '.html' );
					}
					
				} );
				$( '.tabs-inner' ).bind( 'contextmenu' , function(){
					return false;
				});
				$( '.tabs-inner' ).mousedown( function ( e ) {
					if ( e.which == 3 ) {
						var sel = $( this ).find( '.el-btn > i' );
						$( '.el-btn-ul' ).remove();
						sel.parent().append( 
							'<div class="el-btn-ul">' +
								'<div class="closeall">关闭所有</div>' +
								'<div class="closeall-but">关闭其它</div>' +
							'</div>'
						);
						$( '.closeall' ).click( function () {
							var tabs = $( '.body-tabwrap .tabs li' ),
								_self = $( this );
							_self.parent().remove();
							for ( var c = 0 ; c < tabs.length ; c++ ) {
								if ( tabs.eq( c ).find( '.tabs-title' ).text() != '首页' && tabs.eq( c ).find( '.tabs-title' ).text() != '主界面' ) {
									self.tabs( 'close' , tabs.eq( c ).find( '.tabs-title' ).text() );
								}
							}
						});
						$( '.closeall-but' ).click( function () {
							var tabs = $( '.body-tabwrap .tabs li' ),
								_self = $( this ),
								names = _self.parent().parent().parent().find( '.tabs-title' ).text();
							
							for ( var c = 0 ; c < tabs.length ; c++ ) {
								if ( tabs.eq( c ).find( '.tabs-title' ).text() != '首页' && tabs.eq( c ).find( '.tabs-title' ).text() != '主界面' && tabs.eq( c ).find( '.tabs-title' ).text() != names ) {
									self.tabs( 'close' , tabs.eq( c ).find( '.tabs-title' ).text() );
								}
							}
							_self.parent().css( 'display' , 'none' );
						});
						/*doc.click( function () {
							$( '.el-btn-ul' ).remove();
						} );*/
					}
				} );
			},
			removebutton : function () {
				$( '.body-tabwrap>.tabs-header>.tabs-wrap>ul' ).find( '.removetab' ).remove();
				$( '.body-tabwrap>.tabs-header>.tabs-wrap>ul>li>a' ).append( '<span class="removetab"><i class="icon-remove"></i></span>' );
				$( '.removetab' ).click( function () {
					var _self = $( this );
					exps.removes( _self );
				} );
			},
			closeAll : function(){ 
				/* 关闭全部 */
				var tabs = self.tabs( 'allTabs' );
				$.each( tabs , function( i , n ) {
					self.tabs( 'close' , n.title );
				})
			},
			closeCurrent: function(){ 
				/*  关闭当前 */
				var currentTab = self.tabs( 'getSelected' ),
					currentTabIndex = self.tabs( 'getTabIndex' , currentTab );
				self.tabs( 'close' , currentTabIndex );
			},
			removetab : function ( text ) {
				self.tabs( 'close' , text );
			},
			removes : function ( obj ) {
				self.tabs( 'close' , obj.parent().find( '.tabs-title' ).text() );
			}
		}
	return exps;
}
/* 全局tab框架 END */






/* 封装时间选择器 BEGIN */
$.fn[ 'datetime' ] = function () {
	var self = $( this ),
		settings = {
			required : true,
			currentText :'今天',
			closeText : '关闭',
			okText : '确定',
			width : self.parent().width() + 20,
			disabled : false,
			formatter : function ( date ) {
				var y = date.getFullYear(),
					m = date.getMonth()+1,
					d = date.getDate();
				return y +'-' + ( m < 10 ? '0' + m : m ) + '-' + ( d < 10 ? '0' + d : d );
			},
			onSelect : function ( date ) {
			
			}
		},
		
		exps = {
			init : function ( options ) {
				if ( !self.attr( 'class' ) && !self.attr( 'id' ))
					return 0;
				settings = $.extend( settings , options );
				self.datebox( {
					required : settings.required,
					width:settings.width,
					currentText : settings.currentText,
					closeText : settings.closeText,
					okText : settings.okText,
					disabled : settings.disabled,
					formatter : settings.formatter,
					onSelect : settings.onSelect
				} );
				self.parent().find( '.validatebox-text' ).click(function () {
					self.parent().find( '.textbox-icon' ).click();
				});
			},
			setvalue : function ( time ) {
				self.datebox( 'setValue' , time );
			},
			getvalue : function () {
				return self.datebox( 'getValue' );
			},
			datetimeobject : function () {
				return self;
			}
		}
	return exps;
}
/* 封装时间选择器 END */









/* 封装数字框验证 BEGIN */
/*$.fn[ 'number' ] = function () {
	var self = $( this ),
		settings = {
			disabled : false,
			value : '',
			min : '',
			max : '',
			precision : 2,
			decimalSeparator : ',',
			groupSeparator : ',',
			prefix : '',
			suffix : '',
			onChange : function ( newValue , oldValue ) {
			
			}
		},
		
		exps = {
			init : function ( options ) {
				if ( !self.attr( 'class' ) && !self.attr( 'id' ))
					return 0;
				settings = $.extend( settings , options );
				self.numberbox( {
					
				} );
			},
			destroy : function () {
				self.numberbox( 'destroy' );
			},
			disable : function () {
				self.numberbox( 'disable' );
			},
			enable : function () {
				self.numberbox( 'enable' );
			},
			setvalue : function ( number ) {
				self.numberbox( 'setValue' , number);
			},
			getvalue : function () {
				self.numberbox( 'getValue' );
			},
			clear : function () {
				self.numberbox( 'clear' );
			},
			reset : function ( number ) {
				self.numberbox( 'reset' , number );
			}
		}
	return exps;
}*/
/* 封装数字框验证 END */







/* 封装选项卡 tabS BEGIN */

/* 封装选项卡 tabS END */












/* 文本验证及提示 BEGIN */
var checkinput_timeout;
$.fn[ 'checkinput' ] = function () {
	var self = $( this ),
		selves = $( this ),
		settings = {
			_length : '',
			_height : '',
			_width : '',
			wokennotnoll : '不能为空',
			wokenerror : '格式错误',
			labblepath : '',
			_inputwidth : 165,
			type : '',
			must : true,
			fnafter : function () {
			
			},
			disabled : false
		},
		exps = {
			init : function ( options ) {
				if ( !self.attr( 'class' ) && !self.attr( 'id' ) )
					return 0;
				if ( self.parent().find( '.input-container' ).length > 0 ) {
					return 0;
				}
				settings = $.extend( settings , options );
				var _html = '',
					disable = ( settings.disabled ? 'disabled' : '' );
				if ( self.attr( 'data-count' ) != undefined && self.attr( 'type' ) == 'radio' ) {
					_html = '';
					for ( var c = 0 ; c < self.attr( 'data-count' ) ; c++ ) {
						_html = _html + '<div class="input-radio-container">' +
											'<input type="' + self.attr( 'type' ) + '" value="' + self.attr( 'data-value' ).split( ',' )[ c ] + '" name="' + self.attr( 'name' ) + '" ' + disable + ' />' +
											'<div class="input-radio-title">' + 
												self.attr( 'data-radiotitle' ).split( ',' )[ c ] + 
												'<div class="input-radio-value" style="display:none">' +
													self.attr( 'data-value' ).split( ',' )[ c ] + 
												'</div>' +
											'</div>' +
										'</div>';
					}
				}
				if ( self.attr( 'data-count' ) != undefined && self.attr( 'type' ) == 'checkbox' ) {
					_html = '';
					for ( var c = 0 ; c < self.attr( 'data-count' ) ; c++ ) {
						_html = _html + '<div class="input-radio-container">' +
											'<input type="' + self.attr( 'type' ) + '" value="' + self.attr( 'data-value' ).split( ',' )[ c ] + '" name="' + self.attr( 'name' ) + '" ' + disable + ' />' + 
											'<div class="input-radio-title">' + 
												self.attr( 'data-radiotitle' ).split( ',' )[ c ] + 
												'<div class="input-radio-value" style="display:none">' +
													self.attr( 'data-value' ).split( ',' )[ c ] + 
												'</div>' +
											'</div>' +
										'</div>';
					}
				}
				if ( self.attr( 'type' ) == 'text' || self.attr( 'type' ) == undefined || self.attr( 'type' ) == '' || self.attr( 'type' ) == 'password' ) {
					_html = '<input class="' + self.attr( 'class' ) + '-input" id="' + self.attr( 'id' ) + '-input"  type="' + self.attr( 'type' ) + '" value="' + self.val() + '" ' + disable + ' style="width:' + settings._inputwidth + 'px;" />';
				}
				self.parent().css( 'display' , 'table' );
				var _path = ( settings.labblepath == 'left' ? 'style="float:left;width:' + settings._inputwidth + 'px"' : 'style="display:table;width:100%;position:relative;"' ),
					_labble = ( self.attr( 'title' ) == '' || self.attr( 'title' ) == undefined ) ? '' : '<div class="input-labble">' + self.attr( 'title' ) + '</div>',
					_must = settings.must == false ? '' : '<div class="input-must">*</div>',
					xx = '<div class="input-container">' + 
						 	_labble + _must +
							'<div class="myinput-content" ' + _path + '>' +
								'<div class="input-content">' + _html + '</div>' +
							'</div>' +
						 '</div>';
				self.parent().append( xx );
				self.parent().find( '.input-radio-title' ).on( 'click' , function () {
					var skl = $( this );
					if ( skl.parent().find( 'input' ).attr( 'type' ) == 'checkbox' ) {
						if (  skl.parent().find( 'input' )[ 0 ].checked == true ) {
							 skl.parent().find( 'input' )[ 0 ].checked = false;
						} else {
							skl.parent().find( 'input' )[ 0 ].checked = true;
						}	
					} else if ( skl.parent().find( 'input' ).attr( 'type' ) == 'radio' ) {
						skl.parent().find( '.manager-input' )[ 0 ].checked = true;
					}
				} );
				self.hide();
				exps.warning();
				self =  self.parent().find( '.input-container' );
				self.parent().find( '.input-container input' ).attr( 'id' , '' );
				self.find( 'input' ).addClass( 'manager-input' );
				self.parent().find( '.input-container input' ).attr( 'title' , '' );
				settings.fnafter( self );
			},
			destroy : function () {
				self.remove();
			},
			clear : function () {
				self.parent().find( 'input' ).val( '' );
				self.parent().find( 'input' ).attr( 'checked' , false );
				$( '.myinput-woken' ).remove();
				$( '.reminderror' ).removeClass( 'reminderror' );
			},
			focus : function () {
				self.parent().find( 'input' ).focus();
			},
			getvalue : function () {
				var xxx = self.parent().find( '.input-container input' );
				if ( xxx.attr( 'type' ) == 'text' || xxx.attr( 'type' ) == 'password' ) {
					return xxx.val();
				}
				if ( xxx.attr( 'type' ) == 'radio' ) {
					return self.parent().find( '.input-container input:checked' ).parent().find( '.input-radio-value' ).text();
				}
				if ( xxx.attr( 'type' ) == 'checkbox' ) {
					var arr = new Array(),
						_xxx = self.parent().find( '.input-container input:checked' );
					for ( var c = 0 ; c < _xxx.length ; c++ ) {
						arr[ c ] = eval(_xxx.eq( c ).parent().find( '.input-radio-value' ).text());
					}
					return arr;
				}
				
			},
			setvalue : function ( data ) {
				var xxx = self.parent().find( '.input-container input' );
				for ( var c = 0 ; c < xxx.length ; c++ ) {
					if ( xxx.eq( c ).attr( 'type' ) == 'text' || xxx.eq( c ).attr( 'type' ) == 'password' ) {
						xxx.eq( c ).val( data );
					}
					if ( xxx.eq( c ).attr( 'type' ) == 'radio' ) {
						if ( xxx.eq( c ).parent().find( '.input-radio-value' ).text() == ( data + '' ) ) {
							xxx.eq( c )[ 0 ].checked = true;
						}
					}
					if ( xxx.eq( c ).attr( 'type' ) == 'checkbox' ) {
						var arr = new Array();
						for ( var c = 0 ; c < xxx.length ; c++ ) {
							for ( var k = 0 ; k < data.length ; k++ ) {
								if ( xxx.eq( c ).parent().find( '.input-radio-value' ).text() == data[ k ] ) {
									xxx.eq( c )[ 0 ].checked = true;
									break;
								}
							}
						}
					}
				}
			},
			warning : function () {
				self.parent().find( 'input' ).focus( function () {
					self.parent().find( '.input-content' ).append( ( selves.attr( 'data-warning' ) == undefined ? '' : '<div class="myinput-warning" style="color:#fe6334;clear:both;letter-spacing:1px;position:absolute;padding:4px;background:#fff;box-shadow:0 0 5px rgba(0,0,0,0.6);top:100%;left:0;z-index:100;">' +
						'<i class="icon-quote-right"></i>&nbsp;' + selves.attr( 'data-warning' ) + '</div>' ) );
				} );
				
				self.parent().find( 'input' ).blur( function () {
					$( '.myinput-warning' ).remove();
				} );
			},
			remind : function ( str ) {
				if ( self.parent().find( '.myinput-woken' ).length > 0 ) {
					 checkinput_timeout = clearTimeout( checkinput_timeout );
					 self.parent().find( '.myinput-woken' ).remove();
				}
				self.parent().find( '.input-content' ).append( '<div class="myinput-woken" style="color:#fe6334;clear:both;letter-spacing:1px;"><i class="icon-info-sign"></i>&nbsp;' + str + '</div>' );
				self.parent().find( '.input-content input' ).addClass( 'reminderror' );
				self.parent().find( '.myinput-woken' ).fadeIn( 200 );
				/*checkinput_timeout = setTimeout( function () {
						self.parent().find( '.myinput-woken' ).remove();
						self.parent().find( '.input-content input' ).removeClass( 'reminderror' );
						clearTimeout( checkinput_timeout );
				} , 5500 );*/
				$( '.myinput-woken-button' ).on( 'click' , function () { 
					$( this ).parent().parent().remove();
					clearTimeout( checkinput_timeout );
				} );
			},
			remindremove : function () {
				self.parent().find( '.myinput-woken' ).remove();
				self.parent().find( '.input-content input' ).removeClass( 'reminderror' );
			},
			disabled : function ( txt ) {
				exps.object().attr( 'disabled' , txt );
			},
			validate : function () {
				var xxx = self.find( 'input' ).val();
				if ( xxx.replace( clearspace , '' ).length < 1 && settings.must == true ) {
					exps.remind( settings.wokennotnoll );
				}
			},
			object : function () {
				return self.parent().find( '.input-content > input' );
			}
		}
	return exps;
}
/* 文本验证及提示 END */


/* 搜索框 BRGIN */
/**
 * { 
 *  ajaxurl:'',
 *	fields:{
 *		fkey:'key',
 *		fvalue:'value'
 *	},
 *	title:'',
 *	searcher:function() object,
 *	prompt:'', 输入框提示,
 *  searchflag：'',
 *  autocomplete:false,
 *  width:200,
 *  height:25
 *  
 * }
 * 
 * 接口要求返回值 名称为  rows:[{key:'',value:''},{key:'',value:''}]
 * 
 */
$.fn[ 'eSearchBox' ]=function(){
	var self=$(this),
	sbpanel=null,
	settings={
		ajaxurl:'',
		fields:{
			fkey:'key',
			fvalue:'value'
		},
		searcher:function(value,name){},
		prompt:'',
		searchflag:'',
		autocomplete:false,
		width:200,
		height:25
	},
	exps={
		init:function(options){
			if ( !self.attr( 'class' ) && !self.attr( 'id' ))
				return 0;
			settings = $.extend( settings , options );
			if(self.hasClass("widget-init")){
				return 0;
			}
			if(!self.hasClass("hzyb-searchbox")){
				self.addClass("hzyb-searchbox");
			}
			
			sbpanel=exps.initSearchBoxHtml(self,settings);
		},
		initSearchBoxHtml:function(tag,JsonSetting){
			
			tag.addClass("widget-init");
			var width=JsonSetting.width;
			var inputwidth=JsonSetting.width-25;
			tag.attr("style",'border:0;margin-left: 0px; margin-right: 2px; padding-top: 0px; padding-bottom: 0px; height: 25px; line-height: 25px; width: '+inputwidth+'px;');
			tag.wrap('<span style="display:inline-block; width:'+width+'px;"><div class="box" style="border:1px solid #95B8E7;width:'+width+'px; "></div></span>');
			tag.attr("placeholder",JsonSetting.prompt);
			var spobj=$('<span/>');
			tag.after(spobj);
			var alink=$('<i class="icon-search icon-large " style="color:#3B9FE3;"></i>');
			spobj.after(alink);
			alink.click(function(){
				var val=tag.attr("fkey");
				if(val==null || val==undefined){
					val=tag.val();
				}
				JsonSetting.searcher(val,JsonSetting.searchflag);
			});
			var selectpanel=null;
			if(JsonSetting.autocomplete){
				selectpanel=$('<div class="searchbox-panel" style="display:none;background-color:#FFFFFF; z-index:10000;position:absolute; border:1px solid #95B8E7; width:'+width+'px;height:200px; overflow:auto;"/>');
				var ulobj=$('<ul style="display:inline;"></ul>');
				selectpanel.append(ulobj);
				tag.parent().after(selectpanel);

				tag.focus(function(){
					$( '.searchbox-panel' ).hide();
					if(tag.val().trim().length<3 || JsonSetting.ajaxurl.length<4){
						return;
					}
					exps.requestData(tag,alink,selectpanel,ulobj,JsonSetting);
				});
				$(document).click(function(e){
					if(!$(e.target).hasClass("searchbox-panel") && 
							! $(e.target).hasClass("hzyb-searchbox") &&
							$(e.target)[0]!==tag[0]){
						selectpanel.hide();
					}
					
				});
				
				tag.keyup(function(event){
					
					var sb=$(this);

					
					if(event.which==40){
						
						var liobj=ulobj.find(".seleted-item-selected");
						if(liobj.next()!=null && liobj.next()!=undefined &&
								liobj.next()[0]!=null && liobj.next()[0]!=undefined &&
								liobj.next()[0].tagName!=undefined &&
								liobj.next()[0].tagName=="LI"){
							
							liobj.removeClass("seleted-item-selected");
							liobj.next().addClass("seleted-item-selected");
							sb.attr("fkey",liobj.next().attr("fkey"));
							sb.val(liobj.next().text());
							var stop=liobj.next().innerHeight()+ liobj.next().offset().top-selectpanel.offset().top;
							if(stop >selectpanel.innerHeight()){
								var scrolltop=selectpanel.scrollTop()+2+stop -selectpanel.innerHeight()
								selectpanel.scrollTop(scrolltop);
							}
							
						}
						return;
					}else if(event.which==38){
						var liobj=ulobj.find(".seleted-item-selected");
						if(liobj.prev() !=null && liobj.prev()!=undefined &&
								liobj.prev()[0]!=null && liobj.prev()[0]!=undefined &&
								liobj.prev()[0].tagName!=undefined &&
								liobj.prev()[0].tagName=="LI"){
							liobj.removeClass("seleted-item-selected");
							liobj.prev().addClass("seleted-item-selected");
							sb.attr("fkey",liobj.prev().attr("fkey"));
							sb.val(liobj.prev().text());
							
							if(liobj.prev().offset().top<selectpanel.offset().top){
								selectpanel.scrollTop(selectpanel.offset().top-liobj.prev().innerHeight()-liobj.prev().offset().top);
							}
						}
						return;
					}else if(event.which==13){
						/*var liobj=ulobj.find(".seleted-item-selected");*/
						
						selectpanel.hide();
						
						alink.trigger('click');
						
						return;
					}

					if(event.which=='block' || $(this).val().trim().length<3){
						ulobj.children('li').remove();
						selectpanel.hide();
						return;
					}
					if((event.which>=48 && event.which<=57) ||
							(event.which>=65 && event.which<=90) ||
							(event.which==190) || (event.which==189)||
							(event.which>=97 && event.which<=105) ||
							event.which==8) {
						
						if(JsonSetting.ajaxurl.length>3){
							
							exps.requestData(tag,alink,selectpanel,ulobj,JsonSetting);
							
						}
					}
					
					
				});
			}
			
			return selectpanel;
		},
		requestData:function(tag,alink,selectpanel,ulobj,JsonSetting){
			ulobj.children('li').remove();  
			var datav='';
			datav=tag.val();
			
			$("body").reajax({
				url:JsonSetting.ajaxurl,
				type:'GET',
				data:{
					filter:datav
				},
				fnafter:function(data){
					var std="seleted-item-selected";
					$.each(data.content.rows,function(){
						
						ulobj.append('<li class="search-box-item '+std+'" fkey="'+
								this[JsonSetting.fields.fkey]+'">'+
								this[JsonSetting.fields.fvalue]+'</li>');
						std="";
					});
				
					$(".search-box-item").click(function(){
						
						tag.attr("fkey",$(this).attr("fkey"));
						tag.val($(this).text());
						selectpanel.hide();
						alink.trigger('click');
					});
					selectpanel.show();

					
				}
				
			});
		},
		show:function(){
			if(sbpanel!=null)
				sbpanel.show();
		},
		hide:function(){
			if(sbpanel!=null)
				sbpanel.hide();
		}
	};
	return exps;
}
/* 搜索框 END */






/* 下拉树 ecombotree BEGIN */
$.fn[ 'ecombotree' ] = function(){
	var self = $( this ),
	settings = {
		data : '',
		node : {
			_pid : '',
			_id : '',
			_name : '',
			_attributes : []
		},
		width : 165,
		required : false,
		onClick : function () {
			
		},
		onSelect : function () {
			
		}
	},
	exps = {
		init : function( options ){
			settings = $.extend( settings , options );
			self.data( "settings" , settings );
			settings.data=new TreeChilds( settings.data , settings.node ).init( 1 );
			self.combotree( settings );
		},
		getTree : function(){
			return self.combotree( 'tree' );
		},
		getValue : function(){
			var treeobj = self.combotree( 'tree' ),
				node = treeobj.tree( "getSelected" );
			if ( node == null ) {
				return null;
			}
			return node.id;
		},
		setValue : function( value ){
			self.combotree( "setValue" , '--' );
			self.combotree( "setValue" , value );
			var _node = self.combotree( "tree" ).tree( 'find' , value );
			self.combotree( "tree" ).tree( 'expandTo' , _node.target );
		},
		object : function(){
			return self;
		},
		gettree : function () {
			return self.combotree( 'tree' );
		}
	};
	return exps;
};
/* 下拉树 END */

/*设置时间按钮组 rsrSetDateBtnGrp BEGIN*/
$.fn[ 'rsrSetDateBtnGrp' ] = function(){
	var self = $( this ),
	settings = {
		format:'yyyy-MM-dd',
		beginDateCtrl:'',
		endDateCtrl:'',
		fnclick:function(){}
	},
	exps = {
		init:function(options){
			settings = $.extend( settings , options );
			var btnshtml='<div class="button-group" style="margin-left: 20px;">'+
					'	<button id="btn_set_yesterday" type="button" class="button button-rounded button-small"'+
					'		style="background: #528dee; height: 25px; line-height: 25px; color: #fff;padding:0 10px;">昨天</button>'+
					'	<button id="btn_set_7day" type="button" class="button button-rounded button-small"'+
					'		style="background: #528dee; height: 25px; line-height: 25px; color: #fff;padding:0 10px;">近7天</button>'+
					'	<button id="btn_set_prev_week" type="button" class="button button-rounded button-small"'+
					'		style="background: #528dee; height: 25px; line-height: 25px; color: #fff;padding:0 10px;">上周</button>'+
					'	<button id="btn_set_mounth" type="button" class="button button-rounded button-small"'+
					'		style="background: #528dee; height: 25px; line-height: 25px; color: #fff;padding:0 10px;">近30天</button>'+
					'	<button id="btn_set_prev_mounth" type="button" class="button button-rounded button-small"'+
					'		style="background: #528dee; height: 25px; line-height: 25px; color: #fff;padding:0 10px;">上月</button>'+
					'</div>';
			self.append(btnshtml);
			
			$("#btn_set_yesterday").click(function(){
				var d=new Date();
				d.setDate(d.getDate()-1); 
				settings.endDateCtrl.datetime().setvalue(d.format(settings.format));
				settings.beginDateCtrl.datetime().setvalue(d.format(settings.format));
				
				exps.onClick();
			});
			
			$("#btn_set_7day").click(function(){
				var d=new Date();
				settings.endDateCtrl.datetime().setvalue(d.format(settings.format));
				d.setDate(d.getDate()-6); 
				settings.beginDateCtrl.datetime().setvalue(d.format(settings.format));
				
				exps.onClick();
			});
			$("#btn_set_prev_week").click(function(){
				var now=new Date();
				var getPrevWeekStartDate=now;
				var getPrevWeekEndDate=now;
				getPrevWeekStartDate.setDate(now.getDate()-now.getDay()); /*new Date(now.getYear(),now.getMonth(),now.getDate()-now.getDay());*/
				settings.endDateCtrl.datetime().setvalue(getPrevWeekStartDate.format(settings.format));
				getPrevWeekEndDate.setDate(getPrevWeekStartDate.getDate()-7);
				settings.beginDateCtrl.datetime().setvalue(getPrevWeekEndDate.format(settings.format));
				
				exps.onClick();
			});
			$("#btn_set_mounth").click(function(){
				var now=new Date();
				settings.endDateCtrl.datetime().setvalue(now.format(settings.format));
				now.setDate(1);
				settings.beginDateCtrl.datetime().setvalue(now.format(settings.format));
				
				exps.onClick();
			});
			$("#btn_set_mounth").click(function(){
				var now=new Date();
				settings.endDateCtrl.datetime().setvalue(now.format(settings.format));
				now.setDate(now.getDate()-29);
				settings.beginDateCtrl.datetime().setvalue(now.format(settings.format));
				
				exps.onClick();
			});
			$("#btn_set_prev_mounth").click(function(){
				var now=new Date();
				now.setDate(now.getDate()-now.getDate());
				settings.endDateCtrl.datetime().setvalue(now.format(settings.format));
				now.setDate(1);
				settings.beginDateCtrl.datetime().setvalue(now.format(settings.format));
				
				exps.onClick();
			});
			
		},
		onClick:function(){
			settings.fnclick();
		}
	};
	return exps;
};
/* 设置时间按钮组 rsrSetDateBtnGrp END */

/* 设置时间按钮组  eSetDateBtnGrp BEGIN */
$.fn[ 'eSetDateBtnGrp' ] = function(){
	var self = $( this ),
	settings = {
		format:'yyyy-MM-dd',
		beginDateCtrl:'',
		endDateCtrl:'',
		fnclick:function(){}
	},
	exps = {
		init:function(options){
			settings = $.extend( settings , options );
			var btnshtml='<div class="button-group" style="margin-left: 20px;">'+
					'	<button id="btn_set_today" type="button" class="button button-rounded button-small"'+
					'		style="background: #528dee; height: 25px; line-height: 25px; color: #fff;padding:0 10px;">今天</button>'+
					'	<button id="btn_set_7day" type="button" class="button button-rounded button-small"'+
					'		style="background: #528dee; height: 25px; line-height: 25px; color: #fff;padding:0 10px;">近7天</button>'+
					'	<button id="btn_set_prev_week" type="button" class="button button-rounded button-small"'+
					'		style="background: #528dee; height: 25px; line-height: 25px; color: #fff;padding:0 10px;">上周</button>'+
					'	<button id="btn_set_mounth" type="button" class="button button-rounded button-small"'+
					'		style="background: #528dee; height: 25px; line-height: 25px; color: #fff;padding:0 10px;">近30天</button>'+
					'	<button id="btn_set_prev_mounth" type="button" class="button button-rounded button-small"'+
					'		style="background: #528dee; height: 25px; line-height: 25px; color: #fff;padding:0 10px;">上月</button>'+
					'</div>';
			self.append(btnshtml);
			
			$("#btn_set_today").click(function(){
				var d=new Date();
				settings.endDateCtrl.datetime().setvalue(d.format(settings.format));
				
				settings.beginDateCtrl.datetime().setvalue(d.format(settings.format));
				
				exps.onClick();
			});
			
			$("#btn_set_7day").click(function(){
				var d=new Date();
				settings.endDateCtrl.datetime().setvalue(d.format(settings.format));
				d.setDate(d.getDate()-6); 
				settings.beginDateCtrl.datetime().setvalue(d.format(settings.format));
				
				exps.onClick();
			});
			$("#btn_set_prev_week").click(function(){
				var now=new Date();
				var getPrevWeekStartDate=now;
				var getPrevWeekEndDate=now;
				getPrevWeekStartDate.setDate(now.getDate()-now.getDay()); /*new Date(now.getYear(),now.getMonth(),now.getDate()-now.getDay());*/
				settings.endDateCtrl.datetime().setvalue(getPrevWeekStartDate.format(settings.format));
				getPrevWeekEndDate.setDate(getPrevWeekStartDate.getDate()-7);
				settings.beginDateCtrl.datetime().setvalue(getPrevWeekEndDate.format(settings.format));
				
				exps.onClick();
			});
			$("#btn_set_mounth").click(function(){
				var now=new Date();
				settings.endDateCtrl.datetime().setvalue(now.format(settings.format));
				now.setDate(1);
				settings.beginDateCtrl.datetime().setvalue(now.format(settings.format));
				
				exps.onClick();
			});
			$("#btn_set_mounth").click(function(){
				var now=new Date();
				settings.endDateCtrl.datetime().setvalue(now.format(settings.format));
				now.setDate(now.getDate()-29);
				settings.beginDateCtrl.datetime().setvalue(now.format(settings.format));
				
				exps.onClick();
			});
			$("#btn_set_prev_mounth").click(function(){
				var now=new Date();
				now.setDate(now.getDate()-now.getDate());
				settings.endDateCtrl.datetime().setvalue(now.format(settings.format));
				now.setDate(1);
				settings.beginDateCtrl.datetime().setvalue(now.format(settings.format));
				
				exps.onClick();
			});
			
		},
		onClick:function(){
			settings.fnclick();
		}
	};
	return exps;
};

/* 设置时间按钮组 eSetDateBtnGrp END */



/* iLoginValidate BWEGIN */ /* 点击登录失败次数过多的话会锁定账户，可以联系管理员输入验证码解锁，验证码为6位，输入后自动后台验证 */
var x = 2;
var test_c = /^([\u4E00-\uFA29]|[\uE7C7-\uE7F3]|[\w])*$/;
$.fn[ 'iLoginValidate' ] = function ( options , array , fn ) {
	if ( !$( this ).attr( 'class' ) )
		return 0;
	var self = $( this ),
		settings = $.extend({
				count : 10, 		/* 登录错误次数 */
				namelength : 16,	/* 用户名长度 */
				pwdlength : 16,		/* 密码长度 */
				isnormal : false,	/* 是否允许使用特殊字符 */
				locktime : 600,		/* 锁定时间 */	
				isajax : ''			/* ajax方法前缀 */
		} , options ),
		_count = settings.count,
		_namelength = settings.namelength,
		_pwdlength = settings.pwdlength,
		_isnormal = settings.isnormal,
		_locktime = settings.locktime,
		_isajax = settings.isajax;
	var kc = 1;
	/* 回车键 */
	document.onkeydown = function( event ){
		var e = event || window.event || arguments.callee.caller.arguments[ 0 ];
		if ( x < _count || x == _count ) {
			if ( e && e.keyCode == 13 && kc == 1 ) {
				ValidateLogin( self , _count , _namelength , _pwdlength , _isnormal , _locktime , _isajax );
			}
		}
	};
	$( '.login_content input' ).focus( function () {
		$( this ).parent().addClass( 'active' );
	} );
	$( '.login_content input' ).blur( function () {
		$( this ).parent().removeClass( 'active' );
	} );
	self.find( '.btn-primary' ).on( 'click' , function () {
		ValidateLogin( self , _count , _namelength , _pwdlength , _isnormal , _locktime , _isajax );
	} );
}
function ValidateLogin( self , _count , _namelength , _pwdlength , _isnormal , _locktime , _isajax ){
	
	var username_input = $( 'input.login_username' ),
		username = username_input.val(),
		userpwd_input = $( 'input.login_pwd' ),
		userpwd = userpwd_input.val();
	if ( username.replace( clearspace , '' ) == '' ) {
		$( 'body' ).RemindWoken( '需输入账号' );
		return;
	} else if ( test_c.test( username.replace( clearspace , '' )  ) == false ) {
		$( 'body' ).RemindWoken( word_c );
		return;
	}
	if ( userpwd.replace( clearspace , '' ) == '' ) {
		$( 'body' ).RemindWoken( '需输入密码' );
		return;
	} else if ( test_c.test( userpwd.replace( clearspace , '' )  ) == false ) {
		$( 'body' ).RemindWoken( word_c );
		 return;
	} else {
		
		$( 'body' ).reajax({
			type : 'POST',
			url : 'token/create.do',
			data : {
				adminCode : username ,
				adminPwd : hex_md5( userpwd )
				//adminPwd :  userpwd 
			},
			fnafter : function( data ) {
				if ( data.content == null || data.content.token == null ) {
					
				} else {
					store.set( 'token' , data.content.token );
					window.location.replace( _url + folder_a + folder_b + folder_f + folder_g + folder_e );
				}
			}
		});
	}
}
/* iLoginValitate END */


/* 普通用户登录 BEGIN */
$.fn[ 'iLoginCustomerValidate' ] = function ( options , array , fn ) {
	if ( !$( this ).attr( 'class' ) )
		return 0;
	var self = $( this ),
		settings = $.extend({
				count : 10, 		/* 登录错误次数 */
				namelength : 16,	/* 用户名长度 */
				pwdlength : 16,		/* 密码长度 */
				isnormal : false,	/* 是否允许使用特殊字符 */
				locktime : 600,		/* 锁定时间 */	
				isajax : ''			/* ajax方法前缀 */
		} , options ),
		_count = settings.count,
		_namelength = settings.namelength,
		_pwdlength = settings.pwdlength,
		_isnormal = settings.isnormal,
		_locktime = settings.locktime,
		_isajax = settings.isajax;
	var kc = 1;
	/* 回车键 */
	document.onkeydown = function( event ){
		var e = event || window.event || arguments.callee.caller.arguments[ 0 ];
		if ( x < _count || x == _count ) {
			if ( e && e.keyCode == 13 && kc == 1 ) {
				ValidateLogin_customer( self , _count , _namelength , _pwdlength , _isnormal , _locktime , _isajax );
			}
		}
	};
	$( '.login_content_customer input' ).focus( function () {
		$( this ).parent().addClass( 'active' );
	} );
	$( '.login_content_customer input' ).blur( function () {
		$( this ).parent().removeClass( 'active' );
	} );
	self.find( '.btn-primary.key-customer' ).on( 'click' , function () {
		ValidateLogin_customer( self , _count , _namelength , _pwdlength , _isnormal , _locktime , _isajax );
	} );
}
function ValidateLogin_customer( self , _count , _namelength , _pwdlength , _isnormal , _locktime , _isajax ){

	var username_input = $( '.login_username_customer > input' ),
		username = username_input.val(),
		userpwd_input = $( '.login_pwd_customer > input' ),
		userpwd = userpwd_input.val();
	if ( username.replace( clearspace , '' ) == '' ) {
		$( 'body' ).RemindWoken( '需输入账号' );
		return;
	} 
	if ( userpwd.replace( clearspace , '' ) == '' ) {
		$( 'body' ).RemindWoken( '需输入密码' );
		return;
	} else if ( test_c.test( userpwd.replace( clearspace , '' )  ) == false ) {
		$( 'body' ).RemindWoken( word_c );
		 return;
	} else {
		$( 'body' ).reajax({
			type : 'POST',
			url : 'tokencustomer/customer/create.do',
			data : {
				custCode : username ,
				custPwd : hex_md5( userpwd )
			},
			fntarget : function () {
				window.parent.document.location.replace( _url + 'customer' );
			},
			targethtmlclass : '.HZ-CUSTOMER',
			fnafter : function( data ) {
				if ( data.content == null || data.content.token == null ) {
					
				} else {
					store.set( 'token' , data.content.token );
					window.location.replace( _url + folder_a + folder_b + folder_f + folder_p +folder_q + folder_e );
				}
			}
		});
	}
}
/* 普通用户登录 END */










/* 用户个人信息按钮点击事件 BEGIN */
$.fn[ 'ManagerButton' ] = function ( options ) {
	if ( !$( this ).attr( 'class' ) )
		return 0;
	var self= $( this );
	self.click( function () {
		$( '.manager-information' ).epanel().remove();
		$( 'body' ).append( 
				'<div class="manager-information ctr-style-height-1" title="个人信息">' +
					'<div class="ctr-style-19 ctr-style-height-1 ctr-style-padding-bottom ctr-style-relative ctr-style-boder">' +
						'<div class="ctr-style-12 ctr-style-float-left ctr-style-height-1">' +
                        	'<div class="ctr-style-margin-auto ctr-style-height-3 ctr-style-4 ctr-style-margin-top">' +
                            	'<input type="text" id="h-editAdminCode" title="账号" />' +
                            '</div>' +
                            '<div class="ctr-style-margin-auto ctr-style-height-3 ctr-style-4 ctr-style-margin-top">' +
                            	'<input type="text" id="h-editAdminRole" title="角色" />' +
                            '</div>' +
                            '<div class="ctr-style-margin-auto ctr-style-height-3 ctr-style-4 ctr-style-margin-top">' +
                            	'<input type="text" id="h-editAdminName" title="姓名" />' +
                            '</div>' +
                        '</div>' +
                       	'<div class="ctr-style-12 ctr-style-float-left ctr-style-height-1 create-menus-container ctr-style-relative">' +
                        	'<div class="ctr-style-margin-auto ctr-style-height-3 ctr-style-4 ctr-style-margin-top">' +
                            	'<input type="password" id="h-editAdminPwd" title="密码" />' +
                            '</div>' +
                            '<div class="ctr-style-margin-auto ctr-style-height-3 ctr-style-4 ctr-style-margin-top">' +
                            	'<input type="text" id="h-editorg" title="组织" />' +
                            '</div>' +
                            '<div class="ctr-style-margin-auto ctr-style-height-3 ctr-style-4 ctr-style-margin-top">' +
                            	'<input type="text" id="h-editAdminPhone" title="手机号" />' +
                            '</div>' +
                      	'</div>' +
					'</div>' +
					'<div class="ctr-style-19 ctr-style-relative ctr-style-height-2 ctr-style-border-top ctr-style-absolute-bottom-1">' +
						'<div class="manager-ifm-btn mybutton purple ctr-style-margin-auto"><span class="userinfo mybuttonstyle">确定</span></div>' +
					'</div>' +
				'</div>'
		);
		$( '.manager-information' ).epanel().init( {
			_height : 370,
			_width : 600,
			fnafter : function ( wrap ) {
				wrap.find( '#h-editAdminCode' ).checkinput().init({ disabled : true });
				wrap.find( '#h-editAdminRole' ).checkinput().init({ disabled : true });
				wrap.find( '#h-editAdminName' ).checkinput().init();
				wrap.find( '#h-editAdminPwd' ).checkinput().init();
				wrap.find( '#h-editorg' ).checkinput().init({ disabled : true });
				wrap.find( '#h-editAdminPhone' ).checkinput().init();
				
				$( 'body' ).reajax( {
					url : 'administrator/read.do',
					fnafter : function ( data ) {
						wrap.find( '#h-editAdminCode' ).checkinput().setvalue( data.content.administrator.adminCode );
						wrap.find( '#h-editAdminRole' ).checkinput().setvalue( valiadte( data.content.administrator.roleName ) );
						wrap.find( '#h-editAdminName' ).checkinput().setvalue( data.content.administrator.adminName );
						wrap.find( '#h-editAdminPwd' ).checkinput().setvalue( data.content.administrator.adminPwd );
						wrap.find( '#h-editorg' ).checkinput().setvalue( valiadte( data.content.administrator.orgName ) );
						wrap.find( '#h-editAdminPhone' ).checkinput().setvalue( valiadte( data.content.administrator.adminPhone ) );
						$( '.userinfo' ).off( 'click' );
						$( '.userinfo' ).on( 'click' , function () {
							var _pwd = data.content.administrator.adminPwd,
								_pwds = '';
							if ( wrap.find( '#h-editAdminPhone' ).checkinput().getvalue() == '' ) {
								$( 'body' ).RemindWoken( '必须填写手机号' );
								return;
							}
							if ( wrap.find( '#h-editAdminPwd' ).checkinput().getvalue() != '' && _pwd != wrap.find( '#h-editAdminPwd' ).checkinput().getvalue() ) {
								_pwds = hex_md5( wrap.find( '#h-editAdminPwd' ).checkinput().getvalue() );
							} else {
								_pwds = _pwd;
							}
							$( 'body' ).reajax( {
								type : 'POST',
								url : 'administrator/update.do',
								data : {
									adminId : data.content.administrator.adminId,
									adminName : wrap.find( '#h-editAdminName' ).checkinput().getvalue(),
									adminPwd : _pwds,
									adminPhone : wrap.find( '#h-editAdminPhone' ).checkinput().getvalue()
								},
								fnafter : function ( data ) {
									$( 'body' ).RemindWokenSuccess( '修改成功' );
								}
							} );
						} );
					}
				} );
			}
		} );
		$( '.manager-information' ).epanel().showpanel();
		
	} );
}




/* 判断参数是否存在 BEGIN */
function valiadte( str ) {
	return ( ( str == undefined || str == null || str == 'null' ) ? '' : str );
}
/* 判断参数是否存在 END */







/* 退出系统 BEGIN */
$( '.manager-exist' ).on( 'click' , function () {
	$( 'body' ).reajax( {
		url : 'token/delete.do',
		type : 'POST',
		fnafter : function ( data ) {
			if ( data.status == 'SUCCESS' ) {
				store.remove( 'token');
				window.parent.document.location.replace( _url );
			}
		}
	} );
} );
/* 退出系统 END */




/* 隐藏左侧树 BEGIN */
$( '.hide-leftside' ).on( 'click' , function () {
	var _self = $( this );
	if ( _self.parent().hasClass( 'hiddens' ) ) {
		_self.parent().removeClass( 'hiddens' );
		_self.removeClass( 'hiddens' );
		_self.parent().css( 'margin-left' , '0' );
		_self.css( 'right' , '0' );
		_self.html( '<i class="icon-chevron-left"></i>' );
		_self.parent().parent().find( '.ui-left' ).eq( 1 ).removeClass( 'content-fullscreen' );
		
	} else {
		_self.parent().addClass( 'hiddens' );
		_self.addClass( 'hiddens' );
		_self.parent().css( 'margin-left' , -_self.parent().width() + 'px' );
		_self.css( 'right' , '-10px' );
		_self.html( '<i class="icon-chevron-right"></i>' );
		_self.parent().parent().find( '.ui-left' ).eq( 1 ).addClass( 'content-fullscreen' );
		
	}
	
} );
$.fn[ 'hideleftside' ] = function ( options ) {
	var self = $( this ),
		settings = {
			tables : '',
			container : '',
			hidden : false
		};
	settings = $.extend( settings , options );
	if ( settings.hidden ) {
		var sev = $( '.hide-leftside' );
		sev.parent().addClass( 'hiddens' );
		sev.parent().css( 'margin-left' , -sev.parent().width() + 'px' );
		sev.css( 'right' , '-10px' );
		sev.html( '<i class="icon-chevron-right"></i>' );
		sev.parent().parent().find( '.ui-left' ).eq( 1 ).addClass( 'content-fullscreen' );
	}
	$( '.hide-leftside' ).on( 'click' , function () {
		var _self = $( this );
		if ( _self.parent().hasClass( 'hiddens' ) ) {
			_self.parent().removeClass( 'hiddens' );
			_self.parent().css( 'margin-left' , '0' );
			_self.css( 'right' , '0' );
			_self.html( '<i class="icon-chevron-left"></i>' );
			_self.parent().parent().find( '.ui-left' ).eq( 1 ).removeClass( 'content-fullscreen' );
			
		} else {
			_self.parent().addClass( 'hiddens' );
			_self.parent().css( 'margin-left' , -_self.parent().width() + 'px' );
			_self.css( 'right' , '-10px' );
			_self.html( '<i class="icon-chevron-right"></i>' );
			_self.parent().parent().find( '.ui-left' ).eq( 1 ).addClass( 'content-fullscreen' );
			
		}
		var cwidth = settings.container.width();
		settings.tables.table().tableobject().datagrid( 'resize' , { width : cwidth } );
		
	} );
}
/* 隐藏左侧树 END */



/* 悬停提示 BEGIN */
$.fn[ 'poptick' ] = function ( options ) {
	var self = $( this ),
		settings = {
			align : 'top',		/* top,bottom */
			node : {
				id : '',
				text : ''
			},
			obj : [],
			top : 0,
			bottom : 0,
			width : 100
		},
		exps = {
			init : function ( options ) {
				if ( !self.attr( 'class' ) && !self.attr( 'id' ) )
					return 0;
				settings = $.extend( settings , options );
				self.css( 'position' , 'relative' );
				var arr = new Array();
				for ( var c = 0 ; c < settings.obj.length ; c++ ) {
					arr[ c ] = '<div class="poptick-content"><div class="poptick-id">' + settings.obj[ c ][ settings.node.id ] + '</div><div class="poptick-text">' + settings.obj[ c ][ settings.node.text ] + '</div></div>';
				}
				self.append(
					'<div class="poptick align' + settings.align + '" style="position:absolute;display:none;width:' + settings.width + 'px;z-index:9999999">' +
						arr.join( '' ) +
					'</div>'
				);
				
				if ( settings.align == 'top' ) {
					self.find( '.poptick' ).css({
						'left' : self.width()/2 - self.find( '.poptick' ).width()/2 + 'px',
						'top' : settings.top + 'px'
					});
				} else if ( settings.align == 'bottom' ) {
					self.find( '.poptick' ).css({
						'left' : self.width()/2 - self.find( '.poptick' ).width()/2 + 'px',
						'bottom' : settings.bottom + 'px'
					});
				}
				
				self.mouseover( function () {
					$( this ).find( '.poptick' ).show();
					self.mouseout( function ( ev ) {
						var _see = $( this );
						if ( $( ev.target ).hasClass( 'poptick' ) || $( ev.target ).hasClass( 'poptick-text' ) || $( ev.target ).hasClass( 'poptick-id' ) ) {
							return;
						}
						_see.find( '.poptick' ).hide();
					} );
				} );
				$( '.poptick' ).mouseover( function () {
					$( this ).show();
				} );
				$( '.poptick' ).mouseout( function () {
					$( this ).hide();
				} );
			}
		}
	return exps;
}
/* 悬停提示 END */


/* 保留两位小数 BEGIN */
function decimal ( x ) {
	var result = Math.round( x * 100) / 100;
	var s_x = result.toString();
	var pos_decimal = s_x.indexOf( '.' );
	if ( pos_decimal < 0 ) {
		pos_decimal = s_x.length;
		s_x += '.';
	}
	while ( s_x.length <= pos_decimal + 2 ) {
		s_x += '0';
	}
	return s_x;
}
/* 保留两位小数 END  */


/* 登录 */
if ( $( '.ar_cars-LOGIN' ).length > 0 ) {
	$( '.login_content' ).iLoginValidate();
}

/* 管理员信息按钮 */
$( '.manager-inf' ).ManagerButton();

/* 顶部导航 */
$( '.header-main-nav' ).iHeader();

if ( $( '.home' ).length > 0 ) {
	$( 'body' ).reajax( {
		url : 'administrator/read.do',
		fnafter : function ( data ) {
			store.set( 'adminType' , data.content.administrator.adminType );
			$( '.system-user-name' ).html( ( ( data.content.administrator.adminName == '' || data.content.administrator.adminName == null ) ? '超级管理员' : data.content.administrator.adminName ) );
		}
	} );
}





/* MainFunction END */
