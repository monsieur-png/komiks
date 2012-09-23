$(document).ready(function(){
	var actionArr = ['/comics/nextList', '/comics/nextListByUser'];
	var nextListIndex = +$('#nextListAction').val();
	var nextCursor = +$('#nextCursor').val();
	var data = {};

	switch( nextListIndex ){
	case 1:
		data['userName'] = $('#userName').val();
		break;
	}

	//loading of next content when scrolled down
	$(window).bottom({proximity: 0.1});
	$(window).bind('bottom', function() {

	var obj = $(this);
	if ( !obj.data('loading') ) {
		obj.data('loading', true);
		
		var nextCursor = $('#nextCursor').val();
		if (nextCursor === '')
			return;

		data['nextCursor'] =  nextCursor;
		$('#loading').addClass('loading');
		
		$.ajax({
			url : actionArr[nextListIndex],
			data : data,
			dataType : 'json',
			success : function( data ) {
				var comics = data['list'];
				$.each(data['list'], function(index, value) {
					var $div = $('<div/>');
					$div.addClass('comicsItem').addClass('row');
					var comicsUrl = '/comics/' + value['random'] + '/' + value['url'];
					var $link = $('<a/>').attr('href', comicsUrl).attr('target', '_blank');
					var $title = $link.append($('<h1/>').addClass('row').append(value['title']));
					
					var $imgLink = $('<a/>').attr('href', comicsUrl).attr('target', '_blank');
					$div.append( $('<div/>').addClass('span8').append($imgLink.append($('<img/>').attr('src', value['coverBlobKey']))) );
					
					
					var $desc = $('<div/>').addClass('span4');
					$desc.append( $('<p>' + value['description'] + '</p>') );
					
					var userName = value['user']['userName'];
					var $author = $('<span/>').append('by: ');
					$author.append( $('<a/>').attr('href', '/user/'+userName).append(userName));
					$div.append( $desc.append($author) ) ;
					
					
					$title.insertAfter( $('div.comicsItem:last'));
					$div.insertAfter( $title );
					//$div.insertAfter( $('div.comicsItem:last'));
				});

				nextCursor = data['nextCursor'];
				$('#nextCursor').val(nextCursor);
				$('#loading').removeClass('loading');

				if ( nextCursor === null ){
					$('#loading').hide();
					$('#nextCursor').val('');
				}
				else
					$('#nextCursor').val(nextCursor);
				
				obj.data('loading', false);
			},
			error: function(data){
				$('#loading').hide();
				$('#nextCursor').val('');
			}
			});
		}
	
	});
	
});