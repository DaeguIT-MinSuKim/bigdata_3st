select * from sale;
select
		title,category,coName,orderCount*supPrice supplyMony,
		orderCount*sellPrice sellMony,
		orderCount*sellPrice-orderCount*supPrice sellProfits
		from sale;
			select
		title,category,coName,(orderCount*supPrice) supplyMony,
		(orderCount*sellPrice) sellMony,
		((orderCount*sellPrice)-(orderCount*supPrice)) sellProfits
		from sale;
		
		select * from software;