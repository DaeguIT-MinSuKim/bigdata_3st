select * from sale;
	
	select
	title,category,coName,
	sum(orderCount*supPrice) supAmount,
	sum(orderCount*sellPrice) saleAmount,
	sum(orderCount*sellPrice-orderCount*supPrice) saleProfits
	from sale group by title;
	
			select
		title,category,coName,
		sum(orderCount*supPrice) supAmount,
		sum(orderCount*sellPrice) saleAmount,
		sum((orderCount*sellPrice)-((orderCount*supPrice)) saleProfits
		from sale
		where title = '삼국지' group by coName ORDER BY category;
		
			select 
			title,category,coName,
			sum(orderCount*supPrice) supAmount, 
			sum(orderCount*sellPrice) saleAmount, 
			sum((orderCount*sellPrice)-(orderCount*supPrice)) saleProfits 
			from sale 
			where title = '삼국지' 
			group by coName; 
			
drop database swmng;	