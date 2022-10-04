// SPDX-License-Identifier: GPL-3.0
pragma solidity 0.8.4;

contract ImageWarrant {

    // 图片所有者
    address payable public owner;

    // 图片定价
    uint public price;

    // 是否可出售
    bool public isSalability;

    // 部署时确定的图片路径 不能被外部访问
    string private imagePath;

    // 创建一个ImageWarrant
    constructor(string memory _imagePath,uint _price) payable {
        imagePath = _imagePath;
        price = _price;
        isSalability = false;
        owner = payable(msg.sender); // 初始化所有者为创建人
    }

    modifier onlyOwner {
        require(
            msg.sender == owner,
            "Only owner can call this function."
        );
        _;
    }

    // 变更触发的事件
    event ChangeSalability(address bidder, bool isSalability);

    event ChangePrice(address bidder, uint price);

    event BuyPrice(address from,address to, uint price);

    event Give(address from,address to);

    // 定义error
    // 出价低于定价
    error BidNotHighEnough(uint imagePrice);

    // 图片不可出售
    error ImagesNotSale();

    // 修改是否可出售属性
    function modifySalability(bool _isSalability) onlyOwner external {
        isSalability = _isSalability;
        emit ChangeSalability(msg.sender, isSalability);
    }

    // 修改定价
    function modifyPrice(uint imagePrice) onlyOwner external {
        price = imagePrice;
        emit ChangePrice(msg.sender, imagePrice);
    }

    // 查询图片路径
    function getImagePath() onlyOwner external view returns(string memory) {
        return imagePath;
    }

    // 查询图片所有者
    function getOwner() onlyOwner external view returns(address) {
        return owner;
    }

    // 购买图片
    function buy() external payable returns(bool) {
        // 如果不允许购买
        require(isSalability, "Images not for sale.");

        // 如果出价不够高，返还你的钱
        if (msg.value < price)
            revert BidNotHighEnough(price);

        // 将钱发送给当图片所有者
        address oldOwner = owner;
        owner.transfer(msg.value);
        owner = payable(msg.sender);
        price = msg.value;
        isSalability = false;
        emit BuyPrice(oldOwner, msg.sender, msg.value);
        return true;
    }

    // 转赠
    function subgift(address to) onlyOwner external returns(bool) {
        // 将钱发送给当图片所有者
        address oldOwner;
        owner = payable(to);
        isSalability = false;
        emit Give(oldOwner, msg.sender);
        return true;
    }



}
