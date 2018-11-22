package com.capg.paymentwallet.service;

import com.capg.paymentwallet.bean.AccountBean;
import com.capg.paymentwallet.dao.AccountDAOImpl;
import com.capg.paymentwallet.dao.IAccountDao;
import com.capg.paymentwallet.exception.CustomerException;
import com.capg.paymentwallet.exception.CustomerExceptionMessage;

public class AccountServiceImpl implements IAccountService {

	IAccountDao dao = new AccountDAOImpl();

	@Override
	public boolean createAccount(AccountBean accountBean) throws Exception {
		validations(accountBean);
		validateBalance(accountBean.getBalance());
		return dao.createAccount(accountBean);

	}

	@Override
	public boolean deposit(AccountBean accountBean, double depositAmount)
			throws Exception {
		boolean result = false;
		try {
			if (validateBalance(depositAmount)) {

				return dao.deposit(accountBean, depositAmount);

			}
		} catch (CustomerException e) {
			System.out.println(e.getMessage());
		}

		return result;
		// return dao.deposit(accountBean, depositAmount);
	}

	@Override
	public boolean withdraw(AccountBean accountBean, double withdrawAmount)
			throws Exception {

		boolean result = false;
		try {
			if (validateBalance(withdrawAmount)) {

				return dao.withdraw(accountBean, withdrawAmount);

			}
		} catch (CustomerException e) {

			System.out.println(e.getMessage());
		}

		return result;
		// return dao.withdraw(accountBean, withdrawAmount);
	}

	@Override
	public boolean fundTransfer(AccountBean transferingAccountBean,
			AccountBean beneficiaryAccountBean, double transferAmount)
			throws Exception {
		boolean result = false;

		try {
			if (validateBalance(transferAmount)) {

				return dao.fundTransfer(transferingAccountBean, beneficiaryAccountBean,
						transferAmount);

			}
		} catch (CustomerException e) {

			System.out.println(e.getMessage());
		}

		return result;
		
	}

	@Override
	public AccountBean findAccount(int accountId) throws Exception {
		IAccountDao dao = new AccountDAOImpl();
		AccountBean bean = dao.findAccount(accountId);
		return bean;
	}

	public boolean validations(AccountBean accountBean)
			throws CustomerException {
		boolean isValid = false;
		if (accountBean.getCustomerBean().getFirstName().trim().length() < 4) {
			throw new CustomerException(CustomerExceptionMessage.ERROR1);
		} else if (accountBean.getCustomerBean().getLastName().trim().length() < 4) {
			throw new CustomerException(CustomerExceptionMessage.ERROR2);
		} else if (!(accountBean.getCustomerBean().getEmailId()
				.matches("[a-z0-9]+@gmail\\.com"))) {
			throw new CustomerException(CustomerExceptionMessage.ERROR3);
		} else if (!(String.valueOf(accountBean.getCustomerBean().getPhoneNo())
				.matches("(0)?[6-9][0-9]{9}"))) {
			throw new CustomerException(CustomerExceptionMessage.ERROR5);
		} else if (accountBean.getCustomerBean().getAddress().length() == 0) {
			throw new CustomerException(CustomerExceptionMessage.ERROR7);
		} else if (!(accountBean.getCustomerBean().getPanNum()
				.matches("[A-Z]{5}[0-9]{5}"))) {
			throw new CustomerException(CustomerExceptionMessage.ERROR4);
		} else {
			isValid = true;
		}
		return isValid;
	}

	public boolean validateBalance(double balance) throws CustomerException {

		boolean isValid = true;

		if (balance <= 0) {
			isValid = false;
			throw new CustomerException(CustomerExceptionMessage.ERROR6);
		}
		return isValid;

	}

}
