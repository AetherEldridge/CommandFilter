# Command Filter

[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Forge](https://img.shields.io/badge/Forge-1.20.1-brightgreen)](https://files.minecraftforge.net/)

**Command Filter** is a Minecraft Forge mod that allows server administrators to flexibly block or allow players to execute specific commands using regular expressions, thereby enhancing server security and management efficiency.

## ✨ Features

- **Regex-Based Command Filtering**: Supports precise command matching using regular expressions, enabling highly customizable filtering rules.
- **Blacklist & Whitelist Mechanism**: Supports both blacklist and whitelist. The blacklist blocks commands, while the whitelist creates exceptions for specific commands.
- **Dynamic Configuration**: All filtering rules can be dynamically added, removed, or modified via in-game commands or configuration files without requiring a server restart.
- **Operation Logging**: Optionally logs all blocked commands and their executors for auditing and troubleshooting purposes.
- **Permission Control**: All administrative commands default to OP permission level 4, ensuring only administrators can modify filtering rules.

## 📦 Installation

1. Ensure you have installed the **Minecraft Forge** server.
2. Place the mod's JAR file into the server's `mods` folder.
3. Start the server. The mod will automatically generate a default configuration file.

## ⚙️ Configuration

The configuration file is located at `config/cmdfilter-common.toml` in the server's root directory.

```toml
[command_filter]
    # Whether command filtering is enabled
    enabled = true
    # Whether to log blocked commands
    log_blocked_commands = true
    # List of blacklist regex patterns. Matching commands will be blocked (unless also matched by the whitelist)
    blacklist = []
    # List of whitelist regex patterns. Matching commands will be allowed (higher priority than blacklist)
    whitelist = []
```

## 🕹️ In-Game Commands

All administrative commands use /cmdfilter as the root command and require OP permission level 4.

Command Description
/cmdfilter enable Enables the command filter
/cmdfilter disable Disables the command filter
/cmdfilter reload Reloads the configuration file
/cmdfilter blacklist add <pattern> Adds a regular expression to the blacklist
/cmdfilter blacklist remove <pattern> Removes a regular expression from the blacklist
/cmdfilter blacklist list Lists all regular expressions in the blacklist
/cmdfilter whitelist add <pattern> Adds a regular expression to the whitelist
/cmdfilter whitelist remove <pattern> Removes a regular expression from the whitelist
/cmdfilter whitelist list Lists all regular expressions in the whitelist

## 🔧 Development & Building

This project uses Gradle for building. If you are a developer, you can build locally by following these steps:

1. Clone the repository to your local machine.
2. Open a terminal and navigate to the project root directory.
3. Run the build command:

```bash
./gradlew build
```

1. The build artifact will be located in the build/libs/ directory.

🤝 Contributing

Issues and Pull Requests are welcome to help improve this project. Before submitting code, please ensure your coding style is consistent with the project.

📄 License

This project is licensed under the MIT License. You are free to use, modify, and distribute this software, provided the copyright notice is retained.




# 命令过滤器

[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Forge](https://img.shields.io/badge/Forge-1.20.1-brightgreen)](https://files.minecraftforge.net/)

**Command Filter** 是一个 Minecraft Forge 模组，允许服务器管理员通过正则表达式灵活地阻止或允许玩家执行特定命令，从而提升服务器的安全性和管理效率。

## ✨ 功能特性

- **基于正则表达式的命令过滤**：支持使用正则表达式精确匹配命令，实现高度定制化的过滤规则。
- **黑白名单机制**：同时支持黑名单和白名单，黑名单用于阻止命令，白名单用于为特定命令设置例外。
- **动态配置**：所有过滤规则均可通过游戏内命令或配置文件进行动态添加、删除和修改，无需重启服务器。
- **操作日志**：可选地记录所有被阻止的命令及其执行者，便于审计和问题排查。
- **权限控制**：所有管理命令默认需要 OP 权限等级 4，确保只有管理员可以修改过滤规则。

## 📦 安装

1. 确保您已安装 **Minecraft Forge** 服务端。
2. 将本模组的 JAR 文件放入服务端的 `mods` 文件夹中。
3. 启动服务器，模组将自动生成默认配置文件。

## ⚙️ 配置说明

配置文件位于服务端根目录下的 `config/cmdfilter-common.toml`。

```toml
[command_filter]
    # 是否启用命令过滤功能
    enabled = true
    # 是否记录被阻止的命令
    log_blocked_commands = true
    # 黑名单正则表达式列表，匹配的命令将被阻止（除非也被白名单匹配）
    blacklist = []
    # 白名单正则表达式列表，匹配的命令将允许执行（优先级高于黑名单）
    whitelist = []
```

🕹️ 游戏内命令

所有管理命令均以 /cmdfilter 为根命令，需要 OP 权限等级 4。

| 命令                                    | 描述                                           |
|:--------------------------------------|:---------------------------------------------|
| /cmdfilter enable                     | 启用命令过滤器                                      |
| /cmdfilter disable                    | 禁用命令过滤器                                      |
| /cmdfilter reload                     | 重新加载配置文件                                     |
| /cmdfilter blacklist add <pattern>    | 向黑名单添加一个正则表达式                                |
| /cmdfilter blacklist remove <pattern> | 从黑名单中移除一个正则表达式                               |
| /cmdfilter blacklist list             | 列出当前黑名单中的所有正则表达式                             |
| /cmdfilter whitelist add <pattern>    | 向白名单添加一个正则表达式                                |
| /cmdfilter whitelist remove <pattern> | 从白名单中移除一个正则表达式                               |
| /cmdfilter whitelist list             | 列出当前白名单中的所有正则表达式                             |

🔧 开发与构建

本项目使用 Gradle 构建。如果您是开发者，可以通过以下步骤进行本地构建：

1. 克隆仓库到本地。
2. 打开终端，进入项目根目录。
3. 运行构建命令：
```batch
./gradlew build
```
4. 构建产物将位于 build/libs/ 目录下。

🤝 贡献

欢迎提交 Issue 或 Pull Request 来帮助改进这个项目。在提交代码前，请确保您的代码风格与项目保持一致。

📄 许可证

本项目使用 MIT License 许可协议。您可以自由地使用、修改和分发本软件，但需保留版权声明。