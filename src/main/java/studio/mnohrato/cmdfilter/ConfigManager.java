package studio.mnohrato.cmdfilter;

import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ConfigManager
{
	public static class CommonConfig
	{
		public final ForgeConfigSpec.ConfigValue< List< ? extends String > > blacklist;
		public final ForgeConfigSpec.ConfigValue< List< ? extends String > > whitelist;
		public final ForgeConfigSpec.BooleanValue                            enabled;
		public final ForgeConfigSpec.BooleanValue                            logBlockedCommands;
		
		public CommonConfig( @NotNull ForgeConfigSpec.Builder builder )
		{
			builder.comment( "Condig of Command Filter" ).push( "command_filter" );
			
			enabled = builder.comment( "Whether to enable the command filter" ).define( "enabled" , true );
			
			blacklist = builder.comment( "Blacklist regex list" ,
										 "Commands matching these regular expressions will be blocked (unless also matched by the whitelist)" ,
										 "Examples:" ,
										 "  - \"/op.*\" - Blocks all commands starting with /op" ,
										 "  - \"/ban.*\" - Blocks all commands starting with /ban" ,
										 "  - \"/give.*\" - Blocks all /give commands" ,
										 "  - \".*\\$.*\" - Blocks commands containing the $ symbol" )
							   .defineList( "blacklist" , Lists.newArrayList( ) , obj -> obj instanceof String );
			
			whitelist = builder.comment( "Whitelist regex list" ,
										 "Commands matching these regular expressions will be allowed to execute (even if they match the blacklist)" ,
										 "Examples:" ,
										 "  - \"/give @p minecraft:stick.*\" - Allows giving sticks" ,
										 "  - \"/tp @s.*\" - Allows teleporting oneself" ).defineList( "whitelist" , Lists.newArrayList( ) , obj -> obj instanceof String );
			
			logBlockedCommands = builder.comment( "Whether to log blocked commands" ).define( "log_blocked_commands" , true );
			
			builder.pop( );
		}
	}
	
	public static final ForgeConfigSpec SPEC;
	private static final CommonConfig    COMMON;
	private final ForgeConfigSpec.ConfigValue< List< ? extends String > > blacklist;
	private final ForgeConfigSpec.ConfigValue< List< ? extends String > > whitelist;
	private final ForgeConfigSpec.BooleanValue enabled;
	private final ForgeConfigSpec.BooleanValue logBlockedCommands;
	
	static
	{
		final Pair< CommonConfig, ForgeConfigSpec > specPair = new ForgeConfigSpec.Builder( ).configure( CommonConfig::new );
		SPEC   = specPair.getRight( );
		COMMON = specPair.getLeft( );
	}
	
	public ConfigManager()
	{
		this.blacklist = COMMON.blacklist;
		this.whitelist = COMMON.whitelist;
		this.enabled = COMMON.enabled;
		this.logBlockedCommands = COMMON.logBlockedCommands;
	}
	
	public static ForgeConfigSpec getSpec( )
	{
		return SPEC;
	}
	
	public List< ? extends String > getBlacklist( )
	{
		return blacklist.get( );
	}
	
	public void setBlacklist( List<String> newList )
	{
		blacklist.set( newList );
	}
	
	public List< ? extends String > getWhitelist( )
	{
		return whitelist.get( );
	}
	
	public void setWhitelist( List<String> newList )
	{
		whitelist.set( newList );
	}
	
	public boolean isEnabled( )
	{
		return enabled.get( );
	}
	
	public void setEnabled( boolean value )
	{
		enabled.set( value );
	}
	
	public boolean shouldLogBlockedCommands( )
	{
		return logBlockedCommands.get( );
	}
	
	public void save()
	{
		SPEC.save();
	}
	
	public void reload()
	{
		// ForgeConfigSpec 会自动重新加载，只需触发一下
		SPEC.save();
	}
}