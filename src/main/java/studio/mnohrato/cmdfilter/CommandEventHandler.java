package studio.mnohrato.cmdfilter;

import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

public class CommandEventHandler
{
	private static final Logger LOGGER = LogManager.getLogger( CommandEventHandler.class );
	
	@SubscribeEvent( priority = EventPriority.HIGHEST )
	public void onCommand( CommandEvent event )
	{
		// 检查是否启用
		if( !CommandFilter.getConfigManager( ).isEnabled( ) )
		{
			return;
		}
		
		CommandSourceStack source = event.getParseResults( ).getContext( ).getSource( );
		
		// 检查是否是玩家（可选：根据需求，也可以处理控制台命令）
		// 如果只过滤玩家命令，取消下面的注释
		if( !( source.getEntity( ) instanceof ServerPlayer ) )
		{
			return;
		}
		
		String command = getFullCommand( event.getParseResults( ) );
		
		if( command == null || command.isEmpty( ) )
		{
			return;
		}
		
		// 检查黑名单和白名单
		boolean matchesBlacklist = RegexMatcher.matchesAny( command , CommandFilter.getConfigManager( ).getBlacklist( ) );
		boolean matchesWhitelist = RegexMatcher.matchesAny( command , CommandFilter.getConfigManager( ).getWhitelist( ) );
		
		// 如果匹配黑名单且不匹配白名单，则阻止命令
		if( matchesBlacklist && !matchesWhitelist )
		{
			event.setCanceled( true );
			source.sendFailure( Component.translatable( "event.command.blocked" ) );
			
			if( CommandFilter.getConfigManager( ).shouldLogBlockedCommands( ) )
			{
				String playerName = source.getEntity( ) instanceof ServerPlayer ? source.getEntity( ).getName( ).getString( ) : "Console";
				LOGGER.info( "Command blocked - Player: {}, Command: {}" , playerName , command );
			}
		}
	}
	
	/**
	 * 从ParseResults中获取完整的命令字符串
	 */
	@Nullable
	private String getFullCommand( ParseResults< CommandSourceStack > parseResults )
	{
		try
		{
			StringReader reader = ( StringReader )parseResults.getReader( );
			// 获取当前读取位置
			int cursor = reader.getCursor( );
			// 重置读取器位置
			reader.setCursor( 0 );
			// 读取整个字符串
			String fullCommand = reader.getString( );
			// 恢复光标位置
			reader.setCursor( cursor );
			return fullCommand;
		}
		catch( Exception e )
		{
			LOGGER.error( "Failed to get the complete command" , e );
			return null;
		}
	}
}