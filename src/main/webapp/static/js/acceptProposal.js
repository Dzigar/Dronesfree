$(function() {
		function acceptProposal() {
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			$(document).ajaxSend(function(e, xhr, options) {
				xhr.setRequestHeader(header, token);
			});
			var orderId = document.getElementById('orderId').value;
			var proposalId = document.getElementById('accept').value;
			$.ajax({
				url : 'acceptProposal',
				type : 'POST',
				method : 'post',
				dataType : 'json',
				data : {
					proposalId : proposalId
				},
				success : function(response) {
					alert(response);
				}
			})
		}
		$('#accept').click(function() {
			acceptProposal();
		});
	});