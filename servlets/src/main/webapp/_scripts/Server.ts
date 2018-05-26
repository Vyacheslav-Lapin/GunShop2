//noinspection JSUnusedGlobalSymbols
class Server extends BaseServer {

    public getGuns(): Promise<Gun[]> {
        return this.getObject(`gun`);
    }

    public getInstances(): Promise<Instance[]> {
        return this.getObject(`instance`);
    }
}
