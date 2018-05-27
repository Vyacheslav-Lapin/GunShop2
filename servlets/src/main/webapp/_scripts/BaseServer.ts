class BaseServer {

    // noinspection SpellCheckingInspection
    constructor(private readonly method: string = `GET`,
                private readonly baseUrl: string = `/webapi/`) {
    }

    // const fetchAsync = async (url, props) => await (await fetch(url, props)).json();
    protected async getObject<T>(urlPart: string): Promise<T> {
        return (await fetch(this.baseUrl + urlPart, { credentials: "same-origin" })).json()
            .catch(status => Error(`JSON didn't load successfully; error code: ${status}`));
    }
}
