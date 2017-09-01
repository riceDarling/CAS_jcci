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
