//noinspection JSUnusedGlobalSymbols
class Server extends BaseServer {

    public getGuns(): Promise<Array<Gun>> {
        return this.getObject(`gun`);
    }

    //noinspection JSUnusedGlobalSymbols
    public getInstances(): Promise<Array<Instance>> {
        return this.getObject(`instance`);
    }
}
