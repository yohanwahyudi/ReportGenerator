/**

 * DynamicReports - Free Java reporting library for creating reports dynamically

 *

 * Copyright (C) 2010 - 2018 Ricardo Mariaca

 * http://www.dynamicreports.org

 *

 * This file is part of DynamicReports.

 *

 * DynamicReports is free software: you can redistribute it and/or modify

 * it under the terms of the GNU Lesser General Public License as published by

 * the Free Software Foundation, either version 3 of the License, or

 * (at your option) any later version.

 *

 * DynamicReports is distributed in the hope that it will be useful,

 * but WITHOUT ANY WARRANTY; without even the implied warranty of

 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the

 * GNU Lesser General Public License for more details.

 *

 * You should have received a copy of the GNU Lesser General Public License

 * along with DynamicReports. If not, see <http://www.gnu.org/licenses/>.

 */

package com.vdi.reports;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import java.awt.Color;
import java.util.Locale;

import com.lowagie.text.Font;

import net.sf.dynamicreports.report.base.expression.AbstractValueFormatter;
import net.sf.dynamicreports.report.builder.HyperLinkBuilder;
import net.sf.dynamicreports.report.builder.ReportTemplateBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.datatype.BigDecimalType;
import net.sf.dynamicreports.report.builder.style.FontBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.tableofcontents.TableOfContentsCustomizerBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;
import net.sf.dynamicreports.report.definition.ReportParameters;

/**
 * 
 * @author Ricardo Mariaca (r.mariaca@dynamicreports.org)
 */

public class TemplatesNonStatic {
	private StyleBuilder rootStyle;
	private StyleBuilder boldStyle;
	private StyleBuilder italicStyle;
	private StyleBuilder bold22TitleStyle;
	private StyleBuilder boldCenteredStyle;
	private StyleBuilder bold12CenteredStyle;
	private StyleBuilder bold18CenteredStyle;
	private StyleBuilder bold22CenteredStyle;
	private StyleBuilder normalLeftStyle;
	private StyleBuilder normal10LeftStyle;
	private StyleBuilder normal12LeftStyle;
	private StyleBuilder normal18LeftStyle;
	private StyleBuilder normal22LeftStyle;
	private StyleBuilder columnStyle;
	private StyleBuilder columnTitleStyle;
	private StyleBuilder groupStyle;
	private StyleBuilder subtotalStyle;

	private ReportTemplateBuilder reportTemplate;
	private CurrencyType currencyType;
	private ComponentBuilder<?, ?> dynamicReportsComponent;
	private ComponentBuilder<?, ?> footerComponent;

	public TemplatesNonStatic() {
		setTemplates();
	}

	private void setTemplates() {

		rootStyle = stl.style().setPadding(2);
		boldStyle = stl.style(rootStyle).bold();
		italicStyle = stl.style(rootStyle).italic();
		
		boldCenteredStyle = stl.style(boldStyle).setTextAlignment(HorizontalTextAlignment.CENTER,VerticalTextAlignment.MIDDLE);
		bold12CenteredStyle = stl.style(boldCenteredStyle).setFontSize(12);
		bold18CenteredStyle = stl.style(boldCenteredStyle).setFontSize(18);
		bold22CenteredStyle = stl.style(boldCenteredStyle).setFontSize(22);
		
		bold22TitleStyle = stl.style(boldCenteredStyle).setFontSize(22);
		
		normalLeftStyle = stl.style().setTextAlignment(HorizontalTextAlignment.LEFT, VerticalTextAlignment.MIDDLE);
		normal10LeftStyle = stl.style(normalLeftStyle).setFontSize(10);
		normal12LeftStyle = stl.style(normalLeftStyle).setFontSize(12);
		normal18LeftStyle = stl.style(normalLeftStyle).setFontSize(18);
		normal22LeftStyle = stl.style(normalLeftStyle).setFontSize(22);
		
		columnStyle = stl.style(rootStyle).setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);

		columnTitleStyle = stl.style(columnStyle).setBorder(stl.pen1Point())
				.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER).setBackgroundColor(Color.LIGHT_GRAY).bold();

		groupStyle = stl.style(boldStyle).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT);
		subtotalStyle = stl.style(boldStyle).setTopBorder(stl.pen1Point());

		StyleBuilder crosstabGroupStyle = stl.style(columnTitleStyle);
		StyleBuilder crosstabGroupTotalStyle = stl.style(columnTitleStyle).setBackgroundColor(new Color(170, 170, 170));
		StyleBuilder crosstabGrandTotalStyle = stl.style(columnTitleStyle).setBackgroundColor(new Color(140, 140, 140));
		StyleBuilder crosstabCellStyle = stl.style(columnStyle).setBorder(stl.pen1Point());

		TableOfContentsCustomizerBuilder tableOfContentsCustomizer = tableOfContentsCustomizer().setHeadingStyle(0,
				stl.style(rootStyle).bold());

		reportTemplate = template().setLocale(Locale.ENGLISH).setColumnStyle(columnStyle)
				.setColumnTitleStyle(columnTitleStyle).setGroupStyle(groupStyle).setGroupTitleStyle(groupStyle)
				.setSubtotalStyle(subtotalStyle).highlightDetailEvenRows().crosstabHighlightEvenRows()
				.setCrosstabGroupStyle(crosstabGroupStyle).setCrosstabGroupTotalStyle(crosstabGroupTotalStyle)
				.setCrosstabGrandTotalStyle(crosstabGrandTotalStyle).setCrosstabCellStyle(crosstabCellStyle)
				.setTableOfContentsCustomizer(tableOfContentsCustomizer);

		currencyType = new CurrencyType();

		footerComponent = cmp.pageXofY().setStyle(stl.style(boldCenteredStyle).setTopBorder(stl.pen1Point()));

	}

	/**
	 * 
	 * Creates custom component which is possible to add to any report band
	 * component
	 * 
	 */

	public ComponentBuilder<?, ?> createTitleComponent(String label) {
		return cmp.horizontalList()
				.add(dynamicReportsComponent,
						cmp.text(label).setStyle(normal12LeftStyle)
								.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT))
				.newRow().add(cmp.line()).newRow().add(cmp.verticalGap(10));
	}
	
	public ComponentBuilder<?, ?> createTitleComponentDaily(String label, String title) {
		
		dynamicReportsComponent = cmp.horizontalList(
				cmp.text(title).setStyle(bold22TitleStyle)
								.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT));
				
		return cmp.verticalList()
				.add(dynamicReportsComponent,
						cmp.text(label).setStyle(normal10LeftStyle)
								.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT))
				.add(cmp.line()).add(cmp.verticalGap(10));
	}

	public CurrencyValueFormatter createCurrencyValueFormatter(String label) {
		return new CurrencyValueFormatter(label);
	}

	public class CurrencyType extends BigDecimalType {
		private static final long serialVersionUID = 1L;

		@Override
		public String getPattern() {
			return "$ #,###.00";
		}
	}

	private class CurrencyValueFormatter extends AbstractValueFormatter<String, Number> {
		private static final long serialVersionUID = 1L;
		private String label;

		public CurrencyValueFormatter(String label) {
			this.label = label;
		}

		@Override
		public String format(Number value, ReportParameters reportParameters) {
			return label + currencyType.valueToString(value, reportParameters.getLocale());
		}
	}

	public ReportTemplateBuilder getReportTemplate() {
		return reportTemplate;
	}

	public void setReportTemplate(ReportTemplateBuilder reportTemplate) {
		this.reportTemplate = reportTemplate;
	}

	public ComponentBuilder<?, ?> getFooterComponent() {
		return footerComponent;
	}

	public void setFooterComponent(ComponentBuilder<?, ?> footerComponent) {
		this.footerComponent = footerComponent;
	}
}
