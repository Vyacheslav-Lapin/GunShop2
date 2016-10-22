'use strict';

class InstanceCatalog {

    /**
     * @param {string} id
     */
    constructor(id="instancies") {
        /**
         * @private
         * @type HTMLOListElement
         */
        this.targetElement = document.getElementById(id);
    }

    /**
     * @param {Instance} gun
     */
    add(instance) {
        //noinspection JSValidateTypes
        const /** @type HTMLLIElement */ instanceLi = document.createElement("li");

        instanceLi.appendChild(document.createTextNode(instance.gun.name));

        this.targetElement.appendChild(instanceLi);
    }

    /**
     * @param {Array<Gun>} guns
     */
    addAll(instancies=[]) {
        instancies.forEach(this.add.bind(this));
    }
}