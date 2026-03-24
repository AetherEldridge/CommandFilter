package studio.mnohrato.cmdfilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RegexMatcher
{
	private static final Logger LOGGER = LogManager.getLogger( RegexMatcher.class );
	
	/**
	 * 检查输入字符串是否匹配列表中的任一正则表达式
	 *
	 * @param input    要检查的字符串
	 * @param patterns 正则表达式列表
	 * @return 如果匹配任一正则表达式返回true，否则返回false
	 */
	public static boolean matchesAny( String input , List< ? extends String > patterns )
	{
		if( patterns == null || patterns.isEmpty( ) || input == null )
		{
			return false;
		}
		
		for( String patternStr : patterns )
		{
			try
			{
				Pattern pattern = Pattern.compile( patternStr );
				if( pattern.matcher( input ).matches( ) )
				{
					return true;
				}
			}
			catch( PatternSyntaxException e )
			{
				LOGGER.error( "Invalid regex: {} - error: {}" , patternStr , e.getMessage( ) );
			}
		}
		
		return false;
	}
	
	/**
	 * 检查输入字符串是否包含匹配列表中的任一正则表达式
	 *
	 * @param input    要检查的字符串
	 * @param patterns 正则表达式列表
	 * @return 如果任一正则表达式匹配返回true，否则返回false
	 */
	public static boolean findAny( String input , List< ? extends String > patterns )
	{
		if( patterns == null || patterns.isEmpty( ) || input == null )
		{
			return false;
		}
		
		for( String patternStr : patterns )
		{
			try
			{
				Pattern pattern = Pattern.compile( patternStr );
				if( pattern.matcher( input ).find( ) )
				{
					return true;
				}
			}
			catch( PatternSyntaxException e )
			{
				LOGGER.error( "Invalid regex: {} - error: {}" , patternStr , e.getMessage( ) );
			}
		}
		
		return false;
	}
}