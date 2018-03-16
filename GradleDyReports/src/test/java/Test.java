
import static net.sf.dynamicreports.report.builder.DynamicReports.*;

 

import java.math.BigDecimal;

import java.util.Date;

 

import com.reports.dyreports.first.*;

import net.sf.dynamicreports.report.datasource.DRDataSource;

import net.sf.dynamicreports.report.exception.DRException;

import net.sf.jasperreports.engine.JRDataSource;

 

/**

 * @author Ricardo Mariaca (r.mariaca@dynamicreports.org)

 */

public class Test {

 

   public Test() {

      build();

   }

 

   private void build() {

 

      try {

         report()

               .setTemplate(Templates.reportTemplate)

               .ignorePageWidth()

               .columns(

                     col.column("ID", "id", type.integerType()),

                     col.column("Item", "item", type.stringType()),

                     col.column("Quantity", "quantity", type.integerType()),

                     col.column("Unit price", "unitprice", type.bigDecimalType()),

                     col.column("Order date", "orderdate", type.dateType()),

                     col.column("Order date", "orderdate", type.dateYearToFractionType()),

                     col.column("Order year", "orderdate", type.dateYearType()),

                     col.column("Order month", "orderdate", type.dateMonthType()),

                     col.column("Order day", "orderdate", type.dateDayType()))

               .title(Templates.createTitleComponent("DynamicPageWidth"))

               .pageFooter(Templates.footerComponent)

               .setDataSource(createDataSource())

               .show();

      } catch (DRException e) {

         e.printStackTrace();

      }

   }

 

   private JRDataSource createDataSource() {

      DRDataSource dataSource = new DRDataSource("id", "item", "orderdate", "quantity", "unitprice");

      dataSource.add(5, "Notebook", new Date(), 1, new BigDecimal(500));

      dataSource.add(8, "Book", new Date(), 7, new BigDecimal(300));

      dataSource.add(15, "PDA", new Date(), 2, new BigDecimal(250));

      return dataSource;

   }

 

   public static void main(String[] args) {

      new Test();

   }

}
