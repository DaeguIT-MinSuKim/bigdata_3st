select * from sale;


	select
		so.title,so.category,c.coName,sum(orderCount*so.supPrice) supplyMony,
		sum(sa.orderCount*so.sellPrice) sellMony,
		sum(sa.orderCount*so.sellPrice-sa.orderCount*so.supPrice) sellProfits
		from software so,sale sa,company c where so.coName=c.coName and
		so.title=sa.title and so.title = '삼국지' ORDER BY so.category
		
		select
		title,category,coName,(orderCount*supPrice) supplyMony,
		(orderCount*sellPrice) sellMony,
		((orderCount*sellPrice)-(orderCount*supPrice)) sellProfits
		from sale
		where title = '삼국지' ORDER BY category;
		
		