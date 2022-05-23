// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.4.22 <0.9.0;

contract Status {

    string public status;

    event setStatus(string status);

    function setStatusToPrepare() public {
        status = "prepare";

        emit setStatus(status);
    }

    function setStatusToCommit() public {
        status = "commit";

        emit setStatus(status);
    }

    function getStatus() public pure returns (string memory status) {
        return status;
    }

}