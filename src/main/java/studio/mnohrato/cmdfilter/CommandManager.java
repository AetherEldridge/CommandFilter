package studio.mnohrato.cmdfilter;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = CommandFilter.MOD_ID)
public class CommandManager
{
	@SubscribeEvent
	public static void onRegisterCommands( RegisterCommandsEvent event )
	{
		CommandDispatcher< CommandSourceStack > dispatcher = event.getDispatcher( );
		
		dispatcher.register( Commands.literal( "cmdfilter" )
									 .requires( source -> source.hasPermission( 4 ) ) // 需要OP权限等级4
									 .then( Commands.literal( "blacklist" )
													.then( Commands.literal( "add" )
																   .then( Commands.argument( "pattern" , StringArgumentType.string( ) ).executes( CommandManager::addBlacklist ) ) )
													.then( Commands.literal( "remove" )
																   .then( Commands.argument( "pattern" , StringArgumentType.string( ) )
																				  .executes( CommandManager::removeBlacklist ) ) )
													.then( Commands.literal( "list" ).executes( CommandManager::listBlacklist ) ) )
									 .then( Commands.literal( "whitelist" )
													.then( Commands.literal( "add" )
																   .then( Commands.argument( "pattern" , StringArgumentType.string( ) ).executes( CommandManager::addWhitelist ) ) )
													.then( Commands.literal( "remove" )
																   .then( Commands.argument( "pattern" , StringArgumentType.string( ) )
																				  .executes( CommandManager::removeWhitelist ) ) )
													.then( Commands.literal( "list" ).executes( CommandManager::listWhitelist ) ) )
									 .then( Commands.literal( "reload" ).executes( CommandManager::reloadConfig ) )
									 .then( Commands.literal( "enable" ).executes( CommandManager::enableFilter ) )
									 .then( Commands.literal( "disable" ).executes( CommandManager::disableFilter ) ) );
	}
	
	private static int addBlacklist( CommandContext< CommandSourceStack > context ) throws CommandSyntaxException
	{
		String        pattern = StringArgumentType.getString( context , "pattern" );
		ConfigManager config  = CommandFilter.getConfigManager( );
		
		List< String > currentList = new ArrayList<>( config.getBlacklist( ) );
		if( currentList.contains( pattern ) )
		{
			context.getSource( ).sendFailure( Component.translatable( "commands.cmdfilter.blacklist.add.failure.exists", pattern ) );
			return 0;
		}
		
		currentList.add( pattern );
		config.setBlacklist( currentList );
		config.save( );
		
		context.getSource( ).sendSuccess( ( ) -> Component.translatable( "commands.cmdfilter.blacklist.add.success", pattern ), true );
		CommandFilter.LOGGER.info( "Added blacklist pattern: {}" , pattern );
		return 1;
	}
	
	private static int removeBlacklist( CommandContext< CommandSourceStack > context ) throws CommandSyntaxException
	{
		String        pattern = StringArgumentType.getString( context , "pattern" );
		ConfigManager config  = CommandFilter.getConfigManager( );
		
		List< String > currentList = new ArrayList<>( config.getBlacklist( ) );
		if( !currentList.remove( pattern ) )
		{
			context.getSource( ).sendFailure( Component.translatable( "commands.cmdfilter.blacklist.remove.failure.notfound", pattern ) );
			return 0;
		}
		
		config.setBlacklist( currentList );
		config.save( );
		
		context.getSource( ).sendSuccess( ( ) -> Component.translatable( "commands.cmdfilter.blacklist.remove.success", pattern ), true );
		CommandFilter.LOGGER.info( "Removed blacklist pattern: {}" , pattern );
		return 1;
	}
	
	private static int listBlacklist( CommandContext< CommandSourceStack > context )
	{
		List< ? extends String > blacklist = CommandFilter.getConfigManager( ).getBlacklist( );
		
		if( blacklist.isEmpty( ) )
		{
			context.getSource( ).sendSuccess( ( ) -> Component.translatable( "commands.cmdfilter.blacklist.list.empty" ), false );
		}
		else
		{
			context.getSource( ).sendSuccess( ( ) -> Component.translatable( "commands.cmdfilter.blacklist.list.header", blacklist.size() ), false );
			for( int i = 0; i < blacklist.size( ); i++ )
			{
				final int index = i + 1;
				int       finalI = i;
				context.getSource( ).sendSuccess( ( ) -> Component.translatable( "commands.cmdfilter.blacklist.list.entry", index, blacklist.get( finalI ) ), false );
			}
		}
		return 1;
	}
	
	private static int addWhitelist( CommandContext< CommandSourceStack > context ) throws CommandSyntaxException
	{
		String        pattern = StringArgumentType.getString( context , "pattern" );
		ConfigManager config  = CommandFilter.getConfigManager( );
		
		List< String > currentList = new ArrayList<>( config.getWhitelist( ) );
		if( currentList.contains( pattern ) )
		{
			context.getSource( ).sendFailure( Component.translatable( "commands.cmdfilter.whitelist.add.failure.exists", pattern ) );
			return 0;
		}
		
		currentList.add( pattern );
		config.setWhitelist( currentList );
		config.save( );
		
		context.getSource( ).sendSuccess( ( ) -> Component.translatable( "commands.cmdfilter.whitelist.add.success", pattern ), true );
		CommandFilter.LOGGER.info( "Added whitelist pattern: {}" , pattern );
		return 1;
	}
	
	private static int removeWhitelist( CommandContext< CommandSourceStack > context ) throws CommandSyntaxException
	{
		String        pattern = StringArgumentType.getString( context , "pattern" );
		ConfigManager config  = CommandFilter.getConfigManager( );
		
		List< String > currentList = new ArrayList<>( config.getWhitelist( ) );
		if( !currentList.remove( pattern ) )
		{
			context.getSource( ).sendFailure( Component.translatable( "commands.cmdfilter.whitelist.remove.failure.notfound", pattern ) );
			return 0;
		}
		
		config.setWhitelist( currentList );
		config.save( );
		
		context.getSource( ).sendSuccess( ( ) -> Component.translatable( "commands.cmdfilter.whitelist.remove.success", pattern ), true );
		CommandFilter.LOGGER.info( "Removed whitelist pattern: {}" , pattern );
		return 1;
	}
	
	private static int listWhitelist( CommandContext< CommandSourceStack > context )
	{
		List< ? extends String > whitelist = CommandFilter.getConfigManager( ).getWhitelist( );
		
		if( whitelist.isEmpty( ) )
		{
			context.getSource( ).sendSuccess( ( ) -> Component.translatable( "commands.cmdfilter.whitelist.list.empty" ), false );
		}
		else
		{
			context.getSource( ).sendSuccess( ( ) -> Component.translatable( "commands.cmdfilter.whitelist.list.header", whitelist.size() ), false );
			for( int i = 0; i < whitelist.size( ); i++ )
			{
				final int index = i + 1;
				int       finalI = i;
				context.getSource( ).sendSuccess( ( ) -> Component.translatable( "commands.cmdfilter.whitelist.list.entry", index, whitelist.get( finalI ) ), false );
			}
		}
		return 1;
	}
	
	private static int reloadConfig( @NotNull CommandContext< CommandSourceStack > context )
	{
		CommandFilter.getConfigManager( ).reload( );
		context.getSource( ).sendSuccess( ( ) -> Component.translatable( "commands.cmdfilter.reload.success" ), true );
		CommandFilter.LOGGER.info( "Reloaded config through commands" );
		return 1;
	}
	
	private static int enableFilter( @NotNull CommandContext< CommandSourceStack > context )
	{
		CommandFilter.getConfigManager( ).setEnabled( true );
		CommandFilter.getConfigManager( ).save( );
		context.getSource( ).sendSuccess( ( ) -> Component.translatable( "commands.cmdfilter.enable.success" ), true );
		CommandFilter.LOGGER.info( "Enabled command filter through commands" );
		return 1;
	}
	
	private static int disableFilter( @NotNull CommandContext< CommandSourceStack > context )
	{
		CommandFilter.getConfigManager( ).setEnabled( false );
		CommandFilter.getConfigManager( ).save( );
		context.getSource( ).sendSuccess( ( ) -> Component.translatable( "commands.cmdfilter.disable.success" ), true );
		CommandFilter.LOGGER.info( "Disabled command filter through commands" );
		return 1;
	}
}