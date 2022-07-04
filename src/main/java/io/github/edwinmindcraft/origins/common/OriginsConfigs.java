package io.github.edwinmindcraft.origins.common;

import com.electronwill.nightconfig.core.Config;
import com.google.common.collect.ImmutableList;
import io.github.edwinmindcraft.origins.api.origin.Origin;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.Objects;

public class OriginsConfigs {
	public static class Common {
		public Common(ForgeConfigSpec.Builder builder) {}
	}

	public static class Client {
		public Client(ForgeConfigSpec.Builder builder) {}
	}

	public static class Server {

		private final ForgeConfigSpec.ConfigValue<Config> origins;

		public Server(ForgeConfigSpec.Builder builder) {
			this.origins = builder.define("origins", Config.inMemory());
		}

		public boolean isOriginEnabled(ResourceLocation origin) {
			return this.origins.get().getOrElse(ImmutableList.of(origin.toString(), "enabled"), true);
		}

		public boolean isPowerEnabled(ResourceLocation origin, ResourceLocation power) {
			return this.origins.get().getOrElse(ImmutableList.of(origin.toString(), power.toString()), false);
		}

		public boolean updateOriginList(Collection<Origin> origins) {
			boolean changed = false;
			for (Origin origin : origins) {
				if (origin.isSpecial()) //Ignore special origins
					continue;
				if (this.origins.get().add(ImmutableList.of(origin.toString(), "enabled"), true))
					changed = true;
				for (ResourceLocation power : origin.getPowers()) {
					if (this.origins.get().add(ImmutableList.of(origin.toString(), power.toString()), true)) {
						changed = true;
					}
				}
			}
			return changed;
		}
	}

	public static final ForgeConfigSpec COMMON_SPECS;
	public static final ForgeConfigSpec CLIENT_SPECS;
	public static final ForgeConfigSpec SERVER_SPECS;

	public static final Common COMMON;
	public static final Client CLIENT;
	public static final Server SERVER;

	static {
		Pair<Common, ForgeConfigSpec> common = new ForgeConfigSpec.Builder().configure(Common::new);
		Pair<Client, ForgeConfigSpec> client = new ForgeConfigSpec.Builder().configure(Client::new);
		Pair<Server, ForgeConfigSpec> server = new ForgeConfigSpec.Builder().configure(Server::new);
		COMMON_SPECS = common.getRight();
		CLIENT_SPECS = client.getRight();
		SERVER_SPECS = server.getRight();
		COMMON = common.getLeft();
		CLIENT = client.getLeft();
		SERVER = server.getLeft();
	}
}
