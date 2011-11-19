package code.snippet

import net.liftweb._
import common._
import http._
import util.Helpers._
import util._
import scala.xml.NodeSeq
import scala.xml.Group
import net.liftweb.http.js.JsCmd
import org.restlet.resource.ClientResource
import org.w3c.dom.Document
import scala.xml.XML
import java.io.StringWriter
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import javax.xml.parsers.DocumentBuilderFactory
import org.xml.sax.InputSource
import java.io.StringReader
import scala.io.Source
import scala.xml.PrettyPrinter

object NeaToDea {

  object response extends SessionVar[String]("No result yet")

  def render() = {

    var fileHolder: FileParamHolder = null

    def process() = fileHolder match {
      case null => {
        response.set("No result yet")
        S.error("No file")
      }
      case _ => {

        try {
          val cr = new ClientResource("http://localhost:8080/nea2dea")
          
          val data = Source.fromInputStream(fileHolder.fileStream).mkString
          
          val nea = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .parse(new InputSource(new StringReader(data)))

          val result = cr.put(nea, classOf[Document])
          val sw = new StringWriter();
          val tr = TransformerFactory.newInstance().newTransformer();
          tr.transform(new DOMSource(result), new StreamResult(sw));
          
          val printer = new PrettyPrinter(80, 4)
          val xml = printer.format(XML.loadString(sw.toString()))
          
          response.set(xml)
        } catch {
          case e => {
            S.notice(e.getLocalizedMessage())
            response.set("")
          }
        }

        S.redirectTo("/")
      }
    }

    "name=nea" #> SHtml.fileUpload(fileHolder = _) &
      "type=submit" #> SHtml.submit("Transform", process)

  }

  def result(in: NodeSeq): NodeSeq = {
    <textarea style="width: 650px">{ response.get }</textarea>
  }
}