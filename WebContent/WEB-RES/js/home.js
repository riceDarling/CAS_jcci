/* 生成表格 */
var tablerefresh = function ( _index ) {
	
	var _pageNumber = $( 'li.active > .pagenumber' ).html(),
			_pageSize = 20;
	$( 'body' ).reajax({
		url : 'carregister/batch/read.do',
		data : {
			pageNumber : _pageNumber,
			pageSize : _pageSize
		},
		fnafter : function ( data ) {
			
			var tabledata = data.content.carinfos,
					table_arr = new Array();
			/* 此处应该调用控件  因为暂时找不到，用方法代替 */
			for ( var c = 0 ; c < tabledata.rows.length ; c++ ) {
				table_arr.push( '<tr>' +
												'<td class="table_td"><input type="checkbox" value=""></td>' +
												'<td class="center table_td  car_nid">' +  tabledata.rows[ c ].carId  +'</td>' +
												'<td class="center table_td">' + tabledata.rows[ c ].carName + '</td>' +
												'<td class="table_td">' + tabledata.rows[ c ].carBrand + '</td>' +
												'<td class="center table_td">' + tabledata.rows[ c ].carVersion + '</td>' +
												'<td class="center table_td">' + tabledata.rows[ c ].carSdkVersion + '</td>' +
												'<td class="center table_td">' + tabledata.rows[ c ].carUpdateTime + '</td>' +
												'<td class="center table_td">' +
													'<a class="btn btn-info">编辑</a>' +
													'<a class="btn btn-danger btn-car-delete" style="margin-left:6px;">删除</a>' +
												'</td>'  +
											'</tr>'
				);
			}
			$( '.carinfo_table tbody' ).html( table_arr.join( '' ) );
			
			/* 删除行 */
			$( '.btn-car-delete' ).off( 'click' );
			$( '.btn-car-delete' ).on( 'click' , function () {
				var self = $( this ),
						carid = self.parent().parent().find( '.car_nid' ).html();
				$( 'body' ).RemindWokenSelect({
					title : '确定删除此条信息',
					istrue : function () {
						$( 'body' ).reajax({
							url : 'carregister/delete.do',
							data : {
								carId : carid
							},
							fnafter : function ( data2 ) {
								if ( data2.status == 'SUCCESS' ) {
									$( 'body' ).RemindWokenSuccess( data2.message.notice );
									tablerefresh( 1 );
								} else {
									$( 'body' ).RemindWokenError( data2.message.notice );
								} 
								
							}
						});
						
					}
				});
			} );
			
			/* 如果是第一次访问则生成页脚 */
			if ( _index == 1 ) {
				var pageindex = parseInt( tabledata.total/20 );
				if ( tabledata.total%25 > 0 ) {
					pageindex = pageindex + 1;
				}
				
				var pagearr = new Array();
				for ( var c = 1 ; c <= pageindex ; c++ ) {
					if ( c == 1 ) {
						pagearr.push(
								'<li class="pagenumber-li active"><a class="pagenumber">' + c + '</a></li>'
						);
					} else {
						pagearr.push(
								'<li class="pagenumber-li"><a class="pagenumber">' + c + '</a></li>'
						);
					}
				}
				
				$( '.pagenumber-content' ).html(
						'<li class="pagenumber-prev"><a >上页</a></li>' + pagearr.join( '' ) + '<li><a class="pagenumber-next">下页</a></li>'
				);
				
			}
		}
	});
}

/* 新增车辆 */
$( '.btn-create-save' ).click( function () {
	var _carName = $( '#input-carName' ).val(),
		_carBrand = $( '#input-carBrand' ).val(),
		_carVersion = $( '#input-carVersion' ).val(),
		_carSdkVersion = $( '#input-carSdkVersion' ).val();
	/* 条件未知，不做判断 */
	$( 'body' ).reajax({
		url : 'carregister/create.do',
		data : {
			carName : _carName,
			carBrand : _carBrand,
			carVersion : _carVersion,
			carSdkVersion : _carSdkVersion,
			carUpdateTime : ( new Date() ).format( 'yyyy-MM-dd hh:mm:ss' )
		},
		fnafter : function ( data ) {
			
			if ( data.status == 'SUCCESS' ) {
				$( 'body' ).RemindWokenSuccess( data.message.notice );
				tablerefresh( 1 );
			} else {
				$( 'body' ).RemindWokenError( data.message.notice );
			} 
			
		}
	});
} );

/* 点击页码 */
doc.on( 'click' , 'a.pagenumber' , function () {
	$( '.pagenumber-li' ).removeClass( 'active' );
	$( this ).parent().addClass( 'active' );
	tablerefresh( 2 )
} );

tablerefresh( 1 );

