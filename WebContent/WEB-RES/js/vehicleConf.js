
$( 'body' ).reajax({
	url : 'carregister/batch/read.do',
	data : {
		pageNumber : 1,
		pageSize : 10000000
	},
	fnafter : function ( data ) {
		$( '.choose-carinfo' ).ecombobox().init({
			valueField : 'carId',
			textField : 'carName',
			cbdata : data.content.carinfos.rows,
			onSelect : function ( record ) {
				$( '#input-carVersion' ).val( record.carVersion );
				$( '#input-carDoenLoadBgImg' ).val( record.carDoenLoadBgImg );
				$( '#input-carDoenLoadSpeak' ).val( record.carDoenLoadSpeak );
			}
		});
	}
});


var filesize = '', 					/* 文件大小 （转换前） */
	uploadfile = '',				/* 文件名 */
	filesizeable = '',				/* 文件大小 （转换后） */
	timeinter = clearInterval( timeinter ); 	/* 定时器 */

var inputfn = function () {
	doc.on( 'change' , '.upload_file' , function () {
		var _self = $( this );
		filesize = _self.get( 0 ).files[ 0 ];
		uploadfile = _self.val();
		if ( filesize.size > 1024 * 1024 ) {
			filesizeable = ( Math.round( filesize.size * 100 / (1024 * 1024)) / 100 ).toString() + 'MB';
		} else {
			filesizeable = ( Math.round( filesize.size * 100 / 1024) / 100 ).toString() + 'KB'
		}
		$( '#input-carDoenLoadBgImg' ).val( uploadfile );
		$( '.upload-remove' ).on( 'click' , function () {
			$( '.input-filename' ).html( '' );
			$( '.upload_file' ).val( '' );
		} );
	} );
}
inputfn();

$( '.btn-savecarinfo' ).click( function () {
	
	$( 'body' ).RemindWokenSelect( {
		title : '确定保存?',
		istrue : function () {
			
			/* 如果文件域有内容则先上传文件，上传成功后再更新数据库 */
			var imgfile = $( '#input-carDoenLoadBgImg' ).val();
			if ( imgfile != '' ) {
				
				var carfilearr = imgfile.split( '\\' );
				var carfilename = carfilearr[ carfilearr.length - 1 ];
				
				$.ajaxFileUpload({
					url : _url + 'carregister/import.do?token=' + store.get( 'token' ) + '&&carFileName=' + carfilename + '&&carId=' + $( '.choose-carinfo' ).ecombobox().getValue(),
					dataType : 'content',
					secureuri : false,
					fileElementId : 'uploadcarfile',
					success : function ( data , status ) {
						data = $.parseJSON(data.replace(/<.*?>/ig,""));
						if ( data.status == 'SUCCESS' ) {
							$( 'body' ).RemindWokenSuccess( '上传成功' );
							
							var carsfilepath = data.content.carfilepath;
							$( '#input-carDoenLoadBgImg' ).val( carsfilepath );
							/* 保存其它信息 */
							var _carVersion = $( '#input-carVersion' ).val(),
									_carDoenLoadSpeak = $( '#input-carDoenLoadSpeak' ).val();
							$( 'body' ).reajax({
								url : 'carregister/update.do',
								data : {
									carId : $( '.choose-carinfo' ).ecombobox().getValue(),
									carDoenLoadBgImg : carsfilepath,
									carVersion : _carVersion,
									carDoenLoadSpeak : _carDoenLoadSpeak
								},
								fnafter : function ( data2 ) {
									if ( data2.status == 'SUCCESS' ) { 
										$( 'body' ).RemindWokenSuccess( '保存成功' );
									}
								}
							});
						} else {
							$( 'body' ).RemindWokenError( '上传失败' );
						}
					},
					error : function ( data , status , e ) {
						$( 'body' ).RemindWokenError( '上传失败' );
					}
				});
			} else {
				/* 如果文件域没有内容则直接更新数据库 */
				var _carVersion = $( '#input-carVersion' ).val(),
						_carDoenLoadSpeak = $( '#input-carDoenLoadSpeak' ).val();
				$( 'body' ).reajax({
					url : 'carregister/update.do',
					data : {
						carId : $( '.choose-carinfo' ).ecombobox().getValue(),
						carVersion : _carVersion,
						carDoenLoadSpeak : _carDoenLoadSpeak
					},
					fnafter : function ( data ) {
						if ( data.status == 'SUCCESS' ) { 
							$( 'body' ).RemindWokenSuccess( '保存成功' );
						}
					}
				});
				
			}
					
		}
	} );
} );