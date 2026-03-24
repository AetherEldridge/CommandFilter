package studio.mnohrato.cmdfilter;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod( CommandFilter.MOD_ID)
public class CommandFilter
{
	public static final String MOD_ID = "mhcmdfilter";
	public static final Logger LOGGER = LogManager.getLogger( MOD_ID );
	
	
	private static ConfigManager configManager;
	
	public CommandFilter( )
	{
		IEventBus modEventBus = FMLJavaModLoadingContext.get( ).getModEventBus( );
		
		modEventBus.addListener( this::commonSetup );
		
		// 注册配置
		ModLoadingContext.get( ).registerConfig( ModConfig.Type.COMMON , ConfigManager.SPEC , "cmdfilter-common.toml" );
		
		// 初始化配置管理器
		configManager = new ConfigManager( );
	}
	
	private void commonSetup( final FMLCommonSetupEvent event )
	{
		MinecraftForge.EVENT_BUS.register( new CommandEventHandler( ) );
	}
	
	public static ConfigManager getConfigManager( )
	{
		return configManager;
	}
}