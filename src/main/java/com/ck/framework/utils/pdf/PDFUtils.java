package com.ck.framework.utils.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by WeiYuan on 2017/5/9.
 */
@Component
public class PDFUtils {

    @Resource
    VelocityEngine engine;

    public void createHtmlContentToPdf(String htmlContent, OutputStream out) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, out);

        // Avoid discrepances between document title and XMP metadata information.
        //document.addTitle(title);
        writer.createXmpMetadata();
        document.open();

        /*ICC_Profile icc = ICC_Profile.getInstance(new FileInputStream("d:\\sRGB Color Space Profile.icm"));
        writer.setOutputIntents("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1", icc);*/

        XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider();
        fontProvider.register("simsun.ttc");

        CSSResolver cssResolver = new StyleAttrCSSResolver();
        //CssFile cssFile = XMLWorkerHelper.getCSS(new FileInputStream("E:\\git-workspace\\amro-em\\resources\\style.css"));
        cssResolver.addCss(XMLWorkerHelper.getInstance().getDefaultCSS());
        /*CssFilesImpl cssFiles = new CssFilesImpl();
        cssFiles.add(this.getDefaultCSS());
        StyleAttrCSSResolver cssResolver = new StyleAttrCSSResolver(cssFiles);*/

        CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);
        HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
        htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());

        PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
        HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
        CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);

        XMLWorker worker = new XMLWorker(css, true);
        XMLParser p = new XMLParser(worker);
        Reader reader = new StringReader(htmlContent.toString());
        p.parse(reader);
        document.close();
    }

    public void htmlToPDF(Map<String, Object> data, OutputStream out, String templet) {
        engine.init();
        Template template = engine.getTemplate(templet);
//        Properties properties = new Properties();
//        properties.put("input.encoding","utf-8");
//        properties.put("output.encoding", "utf-8");
//        properties.put("resource-loader-path","/templates/");
//        properties.put("resource.loader", "class");
//        properties.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
//
//        VelocityEngine engine = new VelocityEngine(properties);
//        engine.init();
//        Template template = engine.getTemplate(templet);

        VelocityContext context = new VelocityContext();
        Iterator<Map.Entry<String, Object>> entries = data.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, Object> entry = entries.next();
            context.put(entry.getKey(), entry.getValue());
        }
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        try {
            createHtmlContentToPdf(writer.toString(), out);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

}
