package ch.test.http;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
public class ProfileSearchEngineTest {
	private String profileSearchUrl = "http://172.19.1.54";
	private String profileSearch_engine = "/ProfileSearch.json";
	public String testProfileSearchEngine(){
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(profileSearchUrl + profileSearch_engine);
		method.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));   
		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new NameValuePair("Q", "{\"conditionMap\":{\"default_JQ\":{\"query\":\"*:*\"}},\"logicalExpression\":\"{default_JQ}\",\"countQuery\":null,\"sort\":[{\"field\":\"updateDate\",\"sortType\":\"long\",\"reverse\":true}],\"recFrom\":0,\"requestLength\":0,\"executeFrom\":\"Profilengine\",\"key\":\"Akey\",\"topOrder\":null}"));
		method.setRequestBody(params.toArray(new NameValuePair[0]));
		int statusCode = 0;
		try {
			statusCode = client.executeMethod(method);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (statusCode == HttpStatus.SC_OK) {
			Writer writer = new StringWriter();
			Reader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), "UTF-8"));
				char[] buffer = new char[1024];
				int n;
				while ((n = reader.read(buffer)) != -1)
					writer.write(buffer, 0, n);
				return writer.toString();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (reader != null)
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
			if (writer != null)
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return null;
	}
}
//12