drop database swmng;
use swmng;
select * from sale;

select
		title,category,coName,
		sum(orderCount*supPrice) supAmount,
		sum(orderCount*sellPrice) saleAmount,
		sum((orderCount*sellPrice)-(orderCount*supPrice)) saleProfits
		from sale
		group by coName
		ORDER BY category;